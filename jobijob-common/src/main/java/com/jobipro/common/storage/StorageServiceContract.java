package com.jobipro.common.storage;

import com.jobipro.common.dto.storage.ObjectMetadata;

public interface StorageServiceContract {
    String generateUploadUrl(String objectKey, String contentType);

    String generateDownloadUrl(String objectKey);

    ObjectMetadata getObjectMetadata(String objectKey);
}
