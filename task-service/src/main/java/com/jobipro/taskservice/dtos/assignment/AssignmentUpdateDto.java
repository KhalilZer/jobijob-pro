package com.jobipro.taskservice.dtos.assignment;

import com.jobipro.taskservice.enums.TaskAssignmentStatus;
import jakarta.validation.constraints.NotNull;

public record AssignmentUpdateDto(
        @NotNull(message = "Status is required")
        TaskAssignmentStatus status
) {
}
