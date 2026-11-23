package com.jobipro.userservice.controllers;

import com.jobipro.common.dto.internal.InternalWorkerResponse;
import com.jobipro.common.response.GlobalResponse;
import com.jobipro.userservice.services.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/workers")
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping("/internal/{workerPublicId}")
    public ResponseEntity<GlobalResponse<InternalWorkerResponse>> getWorkerInternal(
            @PathVariable String workerPublicId
    ) {
        return GlobalResponse.success(
                workerService.getWorkerById(workerPublicId),
                "Worker retrieved successfully",
                HttpStatus.OK
        );
    }
}