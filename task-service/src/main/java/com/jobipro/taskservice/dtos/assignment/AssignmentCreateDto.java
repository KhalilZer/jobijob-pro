package com.jobipro.taskservice.dtos.assignment;

import jakarta.validation.constraints.NotBlank;

public record AssignmentCreateDto(
        @NotBlank(message = "Worker ID is required")
        String workerId

) {
}