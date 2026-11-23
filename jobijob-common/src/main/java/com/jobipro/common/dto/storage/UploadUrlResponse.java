package com.jobipro.common.dto.storage;

public record UploadUrlResponse(
        String uploadUrl,
        String objectKey
) {
}