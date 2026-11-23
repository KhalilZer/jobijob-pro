package com.jobipro.taskservice.services;

import com.jobipro.common.UserRole;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.dto.internal.InternalWorkerResponse;
import com.jobipro.common.exceptions.ForbiddenException;
import com.jobipro.common.exceptions.ResourceAlreadyExist;
import com.jobipro.common.exceptions.ResourceNotFound;
import com.jobipro.common.response.GlobalResponse;
import com.jobipro.common.utils.AuthorizationUtils;
import com.jobipro.taskservice.clients.UserClient;
import com.jobipro.taskservice.clients.WorkerClient;
import com.jobipro.taskservice.contracts.AssignmentContract;
import com.jobipro.taskservice.dtos.assignment.AssignmentCreateDto;
import com.jobipro.taskservice.dtos.assignment.AssignmentResponseDto;
import com.jobipro.taskservice.dtos.assignment.AssignmentUpdateDto;
import com.jobipro.taskservice.enteties.Task;
import com.jobipro.taskservice.enteties.TaskAssignment;
import com.jobipro.taskservice.enums.AssignmentType;
import com.jobipro.taskservice.enums.TaskAssignmentStatus;
import com.jobipro.taskservice.enums.TaskStatus;
import com.jobipro.taskservice.exceptions.CancellationNotAllowedException;
import com.jobipro.taskservice.exceptions.TaskNotOpenException;
import com.jobipro.taskservice.exceptions.WorkerNotAvailableException;
import com.jobipro.taskservice.mappers.AssignmentMapper;
import com.jobipro.taskservice.repositories.AssignmentRepo;
import com.jobipro.taskservice.repositories.TaskRepo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AssignmentService implements AssignmentContract {

    private final AssignmentRepo assignmentRepo;
    private final TaskService taskService;
    private final WorkerClient workerClient;
    private final TaskRepo taskRepo;
    private final UserClient userClient;
    private final AssignmentMapper assignmentMapper;


    private Task taskAvailable(String taskPublicId) {
        Task task = taskRepo.findByPublicId(taskPublicId)
                .orElseThrow(() ->
                        new ResourceNotFound("Task not found")
                );

        if (task.getStatus() != TaskStatus.OPEN) {
            throw new TaskNotOpenException(
                    "Task is not open for assignments"
            );
        }

        //Check if Task Assignments not (Accepted) si REJECTED OU PENDING les users tasks still OPEN
        Collection<TaskAssignmentStatus> forbiddenAssignmentStatuses =
                List.of(
                        TaskAssignmentStatus.ACCEPTED);

        if (assignmentRepo.existsByTaskAndStatusIn(task, forbiddenAssignmentStatuses)) {
            throw new TaskNotOpenException("Task is no longer available for assignments");
        }

        return task;

    }

    @PreAuthorize("hasAnyRole('COMPANY_OWNER', 'SITE_MANAGER','ADMIN')")
    @Override
    public AssignmentResponseDto invitWorker(String taskPublicId, AssignmentCreateDto assignmentCreateDto) {
        //check if task exist and Open ?
        Task task = this.taskAvailable(taskPublicId);

        //Get initiatedById = current User (Owner / Manager)
        GlobalResponse<InternalUserResponseDto> userResponse = userClient.getConnectedUser();
        InternalUserResponseDto currentUser = userResponse.getData();


        //check OwnerShip
        AuthorizationUtils.requireOwnership(task.getCreatedById(), currentUser.publicId());

        //*************WORKER BLOC*****************
        //Check if WorkerId exist ?
        GlobalResponse<InternalWorkerResponse> workerResponse = workerClient.getWorkerByPublicId(assignmentCreateDto.workerId());
        InternalWorkerResponse worker = workerResponse.getData();

        //Check if there is no assignment for the same worker
        if (assignmentRepo.existsByTaskAndWorkerIdAndStatus(task, worker.publicId(), TaskAssignmentStatus.PENDING)) {
            throw new ResourceAlreadyExist("Worker already has a pending invitation for this task");
        }

        //Check if worker Is available
        if (!worker.available()) {
            throw new WorkerNotAvailableException("Worker is not available");
        }

        //Create assignment
        TaskAssignment taskAssignment = new TaskAssignment();
        taskAssignment.setWorkerId(worker.publicId());
        taskAssignment.setTask(task);
        taskAssignment.setInitiatedById(currentUser.publicId());
        taskAssignment.setAssignmentType(AssignmentType.BY_OWNER);

        return assignmentMapper.toResponse(assignmentRepo.save(taskAssignment), worker, currentUser);

        //TODO : Send Notification to WORKER

    }

    @PreAuthorize("hasRole('WORKER')")
    @Override
    public AssignmentResponseDto requestByWorker(String taskPublicId) {
        //Check for task availability
        Task task = this.taskAvailable(taskPublicId);
        //Get initiatedById = current User (Worker)
        GlobalResponse<InternalUserResponseDto> userResponse = userClient.getConnectedUser();
        InternalUserResponseDto currentUser = userResponse.getData();


        //Check if Worker exists
        GlobalResponse<InternalWorkerResponse> workerResponse = workerClient.getWorkerByPublicId(currentUser.worker().publicId());
        InternalWorkerResponse worker = workerResponse.getData();

        //Check if there is no assignment for the same worker
        if (assignmentRepo.existsByTaskAndWorkerIdAndStatus(task, worker.publicId(), TaskAssignmentStatus.PENDING)) {
            throw new ResourceAlreadyExist("Worker already has a pending invitation for this task");
        }

        //Check if worker is available
        if (!worker.available()) {
            throw new WorkerNotAvailableException("Worker is not available");
        }
        //Create assignment
        TaskAssignment taskAssignment = new TaskAssignment();
        taskAssignment.setWorkerId(worker.publicId());
        taskAssignment.setTask(task);
        taskAssignment.setInitiatedById(currentUser.publicId());
        taskAssignment.setAssignmentType(AssignmentType.BY_WORKER);
        return assignmentMapper.toResponse(assignmentRepo.save(taskAssignment), worker, currentUser);

        //TODO : Send Notification to Owner / Manager

    }

    @Override
    public AssignmentResponseDto updateAssignment(String publicId, AssignmentUpdateDto assignmentUpdateDto) {
        // 1. Assignment exists ?

        TaskAssignment assignment = assignmentRepo.findByPublicId(publicId).orElseThrow(() -> new ResourceNotFound("Assignment Not Found"));

        // 2. Lock the task
        Task task = taskRepo.findByPublicIdForUpdate(
                assignment.getTask().getPublicId()
        ).orElseThrow(() ->
                new ResourceNotFound("Task Not Found"));


        String[] cars = {"sdsfds", "fsdfs"};
        for (String car : cars) {
            System.out.println(car);
        }
        // 2. Connected User
        InternalUserResponseDto currentUser = userClient.getConnectedUser().getData();

        // 3. Status should be different to current Status
        if (assignment.getStatus() == assignmentUpdateDto.status()) {
            throw new IllegalStateException(
                    "Assignment already has this status"
            );
        }

        // 4. can we validate this status change ?
        validateTransition(assignment, assignmentUpdateDto.status(), currentUser);

        // 5. Change status
        assignment.setStatus(assignmentUpdateDto.status());

        // 6. Save
        TaskAssignment saved = assignmentRepo.save(assignment);

        // 7. Return DTOResponse

        return assignmentMapper.toResponse(assignment, currentUser.worker(), currentUser);

    }

    private void validateTransition(TaskAssignment assignment, TaskAssignmentStatus newStatus, InternalUserResponseDto currentUser) {
        TaskAssignmentStatus currentStatus = assignment.getStatus();

        // 1. if task Is Rejected or Canceled we cannot update it
        if (currentStatus == TaskAssignmentStatus.REJECTED ||
                currentStatus == TaskAssignmentStatus.CANCELED) {

            throw new IllegalStateException(
                    "This assignment can no longer be updated"
            );
        }

        if (currentStatus == TaskAssignmentStatus.PENDING) {
            validatePendingTransition(
                    assignment,
                    newStatus,
                    currentUser
            );
            return;
        }

        if (currentStatus == TaskAssignmentStatus.ACCEPTED) {
            validateAcceptedTransition(
                    assignment,
                    newStatus,
                    currentUser
            );
            return;
        }

        throw new IllegalStateException(
                "Invalid assignment status transition"
        );

    }


    private void validateAcceptedTransition(TaskAssignment assignment, TaskAssignmentStatus newStatus, InternalUserResponseDto currentUser) {

        //An ACCEPTED assignment can only be Canceled by  Owner/Manager
        if (newStatus != TaskAssignmentStatus.CANCELED) {
            throw new IllegalStateException(
                    "Accepted assignment can only be canceled"
            );
        }
        boolean isOwnerOrManager =
                currentUser.role() == UserRole.COMPANY_OWNER ||
                        currentUser.role() == UserRole.SITE_MANAGER;

        if (!isOwnerOrManager) {
            throw new ForbiddenException(
                    "Only Owner or Manager can Cancel assignment"
            );
        }
        AuthorizationUtils.requireOwnership(
                assignment.getTask().getCreatedById(),
                currentUser.publicId()
        );


        //Deadline Cancellation = 8hours
        Instant cancellationDeadline =
                assignment.getTask()
                        .getStartDate()
                        .minus(Duration.ofHours(8));

        if (Instant.now().isAfter(cancellationDeadline)) {
            throw new CancellationNotAllowedException(
                    "Assignment cannot be canceled less than 8 hours before task start"
            );
        }
    }

    private void validatePendingTransition(TaskAssignment assignment, TaskAssignmentStatus newStatus, InternalUserResponseDto currentUser) {

        // 1. When status to Update is PENDING we accept (ACCEPTED or REJECTED) only
        if (newStatus != TaskAssignmentStatus.ACCEPTED &&
                newStatus != TaskAssignmentStatus.REJECTED) {

            throw new IllegalStateException(
                    "Pending assignment can only be accepted or rejected"
            );
        }

        //Check if there is an assignment ACCEPTED
        if (newStatus == TaskAssignmentStatus.ACCEPTED &&
                assignmentRepo.existsByTaskAndStatusIn(
                        assignment.getTask(),
                        List.of(TaskAssignmentStatus.ACCEPTED)
                )) {

            throw new TaskNotOpenException(
                    "Task is already assigned to another worker"
            );
        }

        // 2.if OWNER who create Invitation only Worker can update Status
        if (assignment.getAssignmentType() == AssignmentType.BY_OWNER) {

            if (currentUser.role() != UserRole.WORKER ||
                    !Objects.equals(
                            assignment.getWorkerId(),
                            currentUser.worker().publicId()
                    )) {

                throw new ForbiddenException(
                        "Only the invited worker can respond to this assignment"
                );
            }

            //TODO:Send Notification to Owner
        }

        // 3. if WORKER who created Request only OWNER / MANAGER can update status
        if (assignment.getAssignmentType() == AssignmentType.BY_WORKER) {

            if (currentUser.role() != UserRole.COMPANY_OWNER &&
                    currentUser.role() != UserRole.SITE_MANAGER) {

                throw new ForbiddenException(
                        "Only the owner or site manager can respond to this request"
                );
            }

            // Check if this that Assignment for this OWNER/MAANGER
            AuthorizationUtils.requireOwnership(
                    assignment.getTask().getCreatedById(),
                    currentUser.publicId()
            );


            //TODO:Send Notification to Worker
        }


    }

    private void ensureTaskNotAlreadyAssigned(Task task) {

        if (assignmentRepo.existsByTaskAndStatusIn(
                task,
                List.of(TaskAssignmentStatus.ACCEPTED)
        )) {
            throw new TaskNotOpenException(
                    "Task is already assigned to another worker"
            );
        }
    }
}
