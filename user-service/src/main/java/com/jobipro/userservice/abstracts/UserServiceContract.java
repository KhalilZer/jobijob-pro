package com.jobipro.userservice.abstracts;

import com.jobipro.userservice.dtos.user.MeResponse;
import com.jobipro.userservice.dtos.user.CreateUserRequest;
import com.jobipro.userservice.dtos.user.storage.ProfilePhotoUploadRequest;
import com.jobipro.userservice.dtos.user.storage.UploadUrlResponse;

public interface UserServiceContract {

    MeResponse me(String keycloakId);

    MeResponse createUser(CreateUserRequest createUserRequest, String keycloakId, String email, String fullName);

    UploadUrlResponse generateProfilePhotoUploadUrl(
            String keycloakId,
            ProfilePhotoUploadRequest request
    );
}
