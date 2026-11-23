package com.jobipro.taskservice.contracts;

import com.jobipro.common.dto.storage.BatchPhotoUploadRequest;
import com.jobipro.common.dto.storage.PhotoUploadRequest;
import com.jobipro.taskservice.dtos.ConfirmPhotosDto;
import com.jobipro.taskservice.dtos.TaskCreateDto;
import com.jobipro.taskservice.dtos.TaskResponseDto;
import com.jobipro.taskservice.dtos.TaskUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.jobipro.common.dto.storage.UploadUrlResponse;

public interface TaskContract {

    TaskResponseDto createTask(TaskCreateDto taskCreateDto);

    List<TaskResponseDto> getAllTasks();

    TaskResponseDto getOneTask(String publicId);

    TaskResponseDto updateTask(String publicId, TaskUpdateDto taskUpdateDto);

    void deleteTask(String publicId);

    UploadUrlResponse generatePhotoUploadUrl(
            String taskPublicId,
            PhotoUploadRequest photoUploadRequest
    );


    List<UploadUrlResponse> generateUploadUrlBatch(String taskPublicId, BatchPhotoUploadRequest request);

    TaskResponseDto confirmPhotos(
            String taskPublicId,
            ConfirmPhotosDto dto
    );
}
