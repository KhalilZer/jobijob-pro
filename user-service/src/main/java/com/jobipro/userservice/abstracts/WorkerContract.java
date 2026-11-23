package com.jobipro.userservice.abstracts;

import com.jobipro.common.dto.internal.InternalWorkerResponse;
import com.jobipro.userservice.dtos.worker.WorkerResponse;

public interface WorkerContract {
    InternalWorkerResponse getWorkerById(String workerPublicId);
}
