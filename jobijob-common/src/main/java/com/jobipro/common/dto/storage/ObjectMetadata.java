package com.jobipro.common.dto.storage;

public record ObjectMetadata(
        long size,
        String contentType
) {
}