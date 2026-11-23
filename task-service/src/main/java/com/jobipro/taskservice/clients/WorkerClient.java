package com.jobipro.taskservice.clients;

import com.jobipro.common.dto.internal.InternalWorkerResponse;
import com.jobipro.common.response.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service", contextId = "workerClient")
public interface WorkerClient {

    @GetMapping("/api/users/workers/internal/{workerPublicId}")
    GlobalResponse<InternalWorkerResponse> getWorkerByPublicId(@PathVariable String workerPublicId);
}
