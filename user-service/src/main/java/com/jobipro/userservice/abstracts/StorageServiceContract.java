package com.jobipro.userservice.abstracts;

import com.jobipro.userservice.dtos.user.storage.ProfilePhotoUploadRequest;
import com.jobipro.userservice.dtos.user.storage.UploadUrlResponse;

public interface StorageServiceContract {
    String generateUploadUrl(String objectKey, String contentType);

   
}
