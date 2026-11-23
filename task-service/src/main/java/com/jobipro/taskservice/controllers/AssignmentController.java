package com.jobipro.taskservice.controllers;

import com.jobipro.common.response.GlobalResponse;
import com.jobipro.taskservice.contracts.AssignmentContract;
import com.jobipro.taskservice.dtos.assignment.AssignmentCreateDto;
import com.jobipro.taskservice.dtos.assignment.AssignmentResponseDto;
import com.jobipro.taskservice.dtos.assignment.AssignmentUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentContract assignmentService;


    @PostMapping("/tasks/{taskPublicId}/invite")
    public ResponseEntity<GlobalResponse<AssignmentResponseDto>> inviteWorker(
            @PathVariable String taskPublicId,
            @Valid @RequestBody AssignmentCreateDto request
    ) {

        AssignmentResponseDto assignment =
                assignmentService.invitWorker(taskPublicId, request);

        return GlobalResponse.success(
                assignment,
                "Worker invited successfully",
                HttpStatus.CREATED
        );

    }


    @PostMapping("/tasks/{taskPublicId}/request")
    public ResponseEntity<GlobalResponse<AssignmentResponseDto>> requestTask(
            @PathVariable String taskPublicId
    ) {

        AssignmentResponseDto assignment =
                assignmentService.requestByWorker(taskPublicId);

        return GlobalResponse.success(
                assignment,
                "Task requested successfully",
                HttpStatus.CREATED
        );
    }

    /**
     * Update assignment status
     * <p>
     * PENDING -> ACCEPTED
     * PENDING -> REJECTED
     * ACCEPTED -> CANCELED
     */
    @PatchMapping("/{publicId}")
    public ResponseEntity<GlobalResponse<AssignmentResponseDto>> updateAssignment(
            @PathVariable String publicId,
            @Valid @RequestBody AssignmentUpdateDto request
    ) {

        AssignmentResponseDto assignment =
                assignmentService.updateAssignment(
                        publicId,
                        request
                );

        return GlobalResponse.success(
                assignment,
                "Assignment updated successfully",
                HttpStatus.OK
        );
    }
}