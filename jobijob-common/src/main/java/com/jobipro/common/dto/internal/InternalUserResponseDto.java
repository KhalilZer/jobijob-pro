package com.jobipro.common.dto.internal;

import com.jobipro.common.UserRole;

public record InternalUserResponseDto(
        String publicId,
        String fullName,
        String email,
        String phone,
        UserRole role,
        InternalWorkerResponse worker
) {
}
