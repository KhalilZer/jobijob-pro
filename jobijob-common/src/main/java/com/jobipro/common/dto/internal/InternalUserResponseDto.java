package com.jobipro.common.dto.internal;

public record InternalUserResponseDto(
        String publicId,
        String fullName,
        String email,
        String phone
) {
}
