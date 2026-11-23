package com.jobipro.common.dto.storage;

import java.util.List;

public record BatchPhotoUploadRequest(
        List<PhotoUploadRequest> files
) {

}
