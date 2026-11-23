package com.jobipro.common.dto.internal;

public record InternalWorkerResponse(
        String publicId,
        String fullName,
        String email,
        Boolean available
) {
}