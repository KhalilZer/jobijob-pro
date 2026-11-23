package com.jobipro.taskservice.controllers;

import com.jobipro.common.dto.storage.BatchPhotoUploadRequest;
import com.jobipro.common.dto.storage.PhotoUploadRequest;
import com.jobipro.common.dto.storage.UploadUrlResponse;
import com.jobipro.common.response.GlobalResponse;
import com.jobipro.taskservice.dtos.ConfirmPhotosDto;
import com.jobipro.taskservice.dtos.TaskCreateDto;
import com.jobipro.taskservice.dtos.TaskResponseDto;
import com.jobipro.taskservice.dtos.TaskUpdateDto;
import com.jobipro.taskservice.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<GlobalResponse<TaskResponseDto>> createTask(
            @Valid @RequestBody TaskCreateDto taskCreateDto
    ) {
        return GlobalResponse.success(
                taskService.createTask(taskCreateDto),
                "Task created successfully",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<TaskResponseDto>>> getAllTasks() {
        return GlobalResponse.success(
                taskService.getAllTasks(),
                "Tasks retrieved successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<GlobalResponse<TaskResponseDto>> getOneTask(
            @PathVariable String publicId
    ) {
        return GlobalResponse.success(
                taskService.getOneTask(publicId),
                "Task retrieved successfully",
                HttpStatus.OK
        );
    }

    @PatchMapping("/{publicId}")
    public ResponseEntity<GlobalResponse<TaskResponseDto>> updateTask(
            @PathVariable String publicId,
            @Valid @RequestBody TaskUpdateDto taskUpdateDto
    ) {
        return GlobalResponse.success(
                taskService.updateTask(publicId, taskUpdateDto),
                "Task updated successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<GlobalResponse<Void>> deleteTask(
            @PathVariable String publicId
    ) {
        taskService.deleteTask(publicId);

        return GlobalResponse.success(
                null,
                "Task deleted successfully",
                HttpStatus.OK
        );
    }

    @PostMapping("/{publicId}/generate-url-upload")
    public ResponseEntity<GlobalResponse<UploadUrlResponse>> generateUploadUrl(
            @PathVariable String publicId,
            @Valid @RequestBody PhotoUploadRequest photoUploadRequest
    ) {
        return GlobalResponse.success(
                taskService.generatePhotoUploadUrl(
                        publicId,
                        photoUploadRequest
                ),
                "Upload URL generated successfully",
                HttpStatus.OK
        );
    }

    @PostMapping("/{publicId}/generate-batch-url-upload")
    public ResponseEntity<GlobalResponse<List<UploadUrlResponse>>> generateUploadUrlBatch(
            @PathVariable String publicId,
            @Valid @RequestBody BatchPhotoUploadRequest batchPhotoUploadRequest
    ) {
        return GlobalResponse.success(
                taskService.generateUploadUrlBatch(
                        publicId,
                        batchPhotoUploadRequest
                ),
                "Upload Batch URL generated successfully",
                HttpStatus.OK
        );
    }


    @PostMapping("/{publicId}/photos/confirm")
    public ResponseEntity<GlobalResponse<TaskResponseDto>> confirmPhotos(
            @PathVariable String publicId,
            @Valid @RequestBody ConfirmPhotosDto dto
    ) {
        TaskResponseDto response =
                taskService.confirmPhotos(publicId, dto);

        return GlobalResponse.success(
                response,
                "Photos confirmed successfully",
                HttpStatus.OK
        );
    }


}