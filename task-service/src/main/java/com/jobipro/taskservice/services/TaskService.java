package com.jobipro.taskservice.services;

import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.dto.storage.BatchPhotoUploadRequest;
import com.jobipro.common.dto.storage.PhotoUploadRequest;
import com.jobipro.common.dto.storage.UploadUrlResponse;
import com.jobipro.common.enums.CompanyStatus;
import com.jobipro.common.enums.SiteStatus;
import com.jobipro.common.exceptions.ForbiddenException;
import com.jobipro.common.storage.StorageServiceContract;
import com.jobipro.common.utils.AuthorizationUtils;
import com.jobipro.taskservice.clients.CompanyClient;
import com.jobipro.taskservice.clients.SiteClient;
import com.jobipro.common.dto.internal.InternalCompanyResponse;
import com.jobipro.common.dto.internal.InternalSiteResponse;
import com.jobipro.common.exceptions.ResourceNotFound;
import com.jobipro.common.response.GlobalResponse;
import com.jobipro.taskservice.clients.UserClient;
import com.jobipro.taskservice.contracts.TaskContract;
import com.jobipro.taskservice.dtos.*;
import com.jobipro.taskservice.enteties.Task;
import com.jobipro.taskservice.mappers.TaskMapper;
import com.jobipro.taskservice.repositories.TaskRepo;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService implements TaskContract {

    private final TaskRepo taskRepo;
    private final TaskMapper taskMapper;
    private final CompanyClient companyClient;
    private final SiteClient siteClient;
    private final UserClient userClient;
    private final StorageServiceContract storageService;

    @Override
    @PreAuthorize("hasAnyRole('COMPANY_OWNER', 'SITE_MANAGER','ADMIN')")
    @Transactional
    public TaskResponseDto createTask(TaskCreateDto taskCreateDto) {

        // 1. Get current user
        GlobalResponse<InternalUserResponseDto> userResponse =
                userClient.getConnectedUser();

        InternalUserResponseDto internalUser = userResponse.getData();

        // 2. Check authorization and retrieve validated context
        TaskCreationContext context =
                checkCreateAuthorization(internalUser, taskCreateDto);

        // 3. Map DTO to entity
        Task task = taskMapper.fromDtoToTask(taskCreateDto);

        // 4. Set task creator
        task.setCreatedById(internalUser.publicId());

        // 5. Save task
        Task savedTask = taskRepo.save(task);

        // 6. Return response
        return taskMapper.fromTaskToResponse(
                savedTask,
                context.company(),
                context.site(),
                internalUser,
                new ArrayList<>()
        );
    }

    private TaskCreationContext checkCreateAuthorization(
            InternalUserResponseDto internalUser,
            TaskCreateDto taskCreateDto
    ) {
        // 1. Check if company exists
        InternalCompanyResponse company =
                companyClient.getInternalCompany(
                        taskCreateDto.companyId()
                ).getData();

        //1.1 Check if company is active
        if (!CompanyStatus.ACTIVE.equals(company.status())) {
            throw new ResourceNotFound(
                    "Company is not active"
            );
        }
        // 2. Check ownership
        boolean isOwner = Objects.equals(
                internalUser.publicId(),
                company.ownerPublicId()
        );

        // 3. Retrieve site if provided
        InternalSiteResponse site = null;

        if (taskCreateDto.siteId() != null
                && !taskCreateDto.siteId().isBlank()) {

            site = siteClient.getInternalSite(
                    taskCreateDto.siteId()
            ).getData();

            // check if Site is active
            //1.1 Check if company is active
            if (!SiteStatus.ACTIVE.equals(site.status())) {
                throw new ResourceNotFound(
                        "Site is not active"
                );
            }

            // Check if site belongs to company
            if (!Objects.equals(
                    company.publicId(),
                    site.companyPublicId()
            )) {
                throw new ResourceNotFound(
                        "Site does not belong to this company"
                );
            }
        }

        // 4. Company owner can create tasks
        if (isOwner) {
            return new TaskCreationContext(company, site);
        }

        // 5. Non-owner must provide a site
        if (site == null) {
            throw new ForbiddenException(
                    "Only company owners can create tasks without a site"
            );
        }

        // 6. Verify site manager ownership
        AuthorizationUtils.requireOwnership(
                site.managerUserId(),
                internalUser.publicId()
        );

        // 7. Authorization successful
        return new TaskCreationContext(company, site);
    }


    @Override
    public List<TaskResponseDto> getAllTasks() {
        return taskRepo.findAll()
                .stream()
                .map(this::buildTaskResponse)
                .toList();
    }

    @Override
    public TaskResponseDto getOneTask(String publicId) {

        Task task = taskRepo.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFound("Task not found"));

        return buildTaskResponse(task);
    }

    @Override
    @PreAuthorize("hasAnyRole('COMPANY_OWNER', 'SITE_MANAGER','ADMIN')")

    public TaskResponseDto updateTask(
            String publicId,
            TaskUpdateDto taskUpdateDto
    ) {

        Task task = taskRepo.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFound("Task not found"));

        canManipulateTask(task);

        taskMapper.updateTaskFromDto(taskUpdateDto, task);

        taskRepo.save(task);

        return buildTaskResponse(task);
    }

    @Override
    @PreAuthorize("hasAnyRole('COMPANY_OWNER', 'SITE_MANAGER','ADMIN')")
    public void deleteTask(String publicId) {
        Task task = taskRepo.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFound("Task not found"));

        canManipulateTask(task);

        taskRepo.delete(task);

    }

    private void canManipulateTask(Task task) {

        boolean isOwner = companyClient
                .connectedUserIsOwner(task.getCompanyId())
                .getData();

        if (isOwner) {
            return;
        }

        if (task.getSiteId() == null || task.getSiteId().isBlank()) {
            throw new ForbiddenException("Access denied");
        }

        boolean isManager = siteClient
                .connectedUserIsManager(task.getSiteId())
                .getData();

        if (!isManager) {
            throw new ForbiddenException("Access denied");
        }
    }

    private TaskResponseDto buildTaskResponse(Task task) {

        //Get Current User
        GlobalResponse<InternalUserResponseDto> userResponse = userClient.getConnectedUser();
        InternalUserResponseDto internalUser = userResponse.getData();

        GlobalResponse<InternalCompanyResponse> companyResponse =
                companyClient.getInternalCompany(task.getCompanyId());

        InternalCompanyResponse company = companyResponse.getData();

        InternalSiteResponse site = null;

        if (task.getSiteId() != null && !task.getSiteId().isBlank()) {
            GlobalResponse<InternalSiteResponse> siteResponse =
                    siteClient.getInternalSite(task.getSiteId());

            site = siteResponse.getData();
        }


        List<String> photoUrls = task.getPhotos() != null && !task.getPhotos().isEmpty() ? task.getPhotos().stream()
                .map(storageService::generateDownloadUrl)
                .toList() : List.of();

        return taskMapper.fromTaskToResponse(
                task,
                company,
                site,
                internalUser,
                photoUrls
        );
    }

    @Override
    public UploadUrlResponse generatePhotoUploadUrl(
            String taskPublicId,
            PhotoUploadRequest photoUploadRequest
    ) {

        Task task = taskRepo.findByPublicId(taskPublicId)
                .orElseThrow(() -> new ResourceNotFound("Task not found"));


        //Verify if User Exists By Keycloak Id & task est la sienne
        canManipulateTask(task);

        String extension = switch (photoUploadRequest.contentType()) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            default -> throw new BadRequestException("Unsupported image type");
        };

        String objectKey = "tasks/"
                + task.getPublicId()
                + "/"
                + UUID.randomUUID()
                + "."
                + extension;

        String uploadUrl =
                storageService.generateUploadUrl(
                        objectKey,
                        photoUploadRequest.contentType()
                );
        return new UploadUrlResponse(
                uploadUrl,
                objectKey
        );

    }

    @Override
    public List<UploadUrlResponse> generateUploadUrlBatch(String taskPublicId, BatchPhotoUploadRequest request) {
        if (request.files() == null
                || request.files().isEmpty()
                || request.files().size() > 5) {
            throw new BadRequestException("Invalid number of photos");
        }
        Task task = taskRepo.findByPublicId(taskPublicId)
                .orElseThrow(() -> new ResourceNotFound("Task not found"));


        //Verify if User Exists By Keycloak Id & task est la sienne
        canManipulateTask(task);


        return request.files().stream()
                .map(file -> {
                    String extension = switch (file.contentType()) {
                        case "image/jpeg" -> "jpg";
                        case "image/png" -> "png";
                        case "image/webp" -> "webp";
                        default -> throw new BadRequestException(
                                "Unsupported image type"
                        );
                    };

                    String objectKey = "tasks/" + taskPublicId + "/"
                            + UUID.randomUUID() + "." + extension;

                    String uploadUrl = storageService.generateUploadUrl(
                            objectKey,
                            file.contentType()
                    );

                    return new UploadUrlResponse(uploadUrl, objectKey);
                })
                .toList();

    }

    @Override
    @Transactional
    public TaskResponseDto confirmPhotos(
            String taskPublicId,
            ConfirmPhotosDto dto
    ) {
        // 1. Récupérer la tâche avec verrouillage
        Task task = taskRepo.findByPublicIdForUpdate(taskPublicId)
                .orElseThrow(() ->
                        new ResourceNotFound("Task not found")
                );

        // 2. Vérifier les permissions
        canManipulateTask(task);

        // 3. Récupérer les photos existantes
        List<String> photos = new ArrayList<>(
                task.getPhotos() == null
                        ? List.of()
                        : task.getPhotos()
        );

        // 4. Éliminer les doublons de la requête
        List<String> newKeys = dto.objectKeys()
                .stream()
                .distinct()
                .filter(key -> !photos.contains(key))
                .toList();

        // 5. Vérifier le maximum de 5 photos
        if (photos.size() + newKeys.size() > 5) {
            throw new BadRequestException(
                    "Maximum 5 photos allowed per task"
            );
        }

        // 6. Vérifier chaque fichier dans MinIO
        for (String objectKey : newKeys) {

            String expectedPrefix = "tasks/" + taskPublicId + "/";

            if (objectKey == null ||
                    !objectKey.startsWith(expectedPrefix)) {

                throw new ForbiddenException(
                        "Photo does not belong to this task"
                );
            }


            var metadata = storageService.getObjectMetadata(objectKey);

            // Vérifier la taille (exemple : 5 Mo maximum)
            if (metadata.size() <= 0 ||
                    metadata.size() > 5 * 1024 * 1024) {
                throw new BadRequestException(
                        "Invalid photo size"
                );
            }

            // Vérifier le type MIME déclaré
            if (!Set.of(
                    "image/jpeg",
                    "image/png",
                    "image/webp"
            ).contains(metadata.contentType())) {
                throw new BadRequestException(
                        "Unsupported photo type"
                );
            }


        }

        // 7. Ajouter les nouvelles photos
        photos.addAll(newKeys);

        // 8. Mettre à jour la tâche
        task.setPhotos(photos);

        Task savedTask = taskRepo.save(task);

        // 9. Construire la réponse
        return buildTaskResponse(savedTask);
    }
}