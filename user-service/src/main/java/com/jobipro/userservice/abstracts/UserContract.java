package com.jobipro.userservice.abstracts;

import com.jobipro.common.dto.internal.InternalUserRequestDto;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.userservice.dtos.user.*;
import com.jobipro.common.dto.storage.PhotoUploadRequest;
import com.jobipro.common.dto.storage.UploadUrlResponse;

public interface UserContract {

    MeResponse me(String keycloakId);

    MeResponse createUser(CreateUserRequest createUserRequest, String keycloakId, String email, String fullName);

    UploadUrlResponse generateProfilePhotoUploadUrl(
            String keycloakId,
            PhotoUploadRequest request
    );


    MeResponse updateUser(String publicId, UpdateUserRequest updateUserRequest);

    //Internal Client Contracts
    InternalUserResponseDto meInternal(String keycloakId);

    InternalUserResponseDto createInternalUser(InternalUserRequestDto internalCreateUserDto);
}
