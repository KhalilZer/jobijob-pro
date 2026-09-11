package com.jobipro.userservice.dtos.user.storage;

public record UploadUrlResponse(
        String uploadUrl,
        String objectKey
) {
}