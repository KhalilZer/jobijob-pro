package com.jobipro.userservice.dtos.user;

import com.jobipro.userservice.dtos.worker.WorkerResponse;
import com.jobipro.userservice.enums.UserStatus;

import java.time.Instant;
import java.time.LocalDate;

public record MeResponse(
        UserResponse userProfile,
        boolean isWorker,
        WorkerResponse workerProfile,

        int profileCompletion


) {
}
