package com.jobipro.taskservice.dtos.assignment;

import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.dto.internal.InternalWorkerResponse;
import com.jobipro.taskservice.enums.TaskAssignmentStatus;

import java.time.Instant;

public record AssignmentResponseDto(

        String publicId,

        InternalWorkerResponse worker,

        InternalUserResponseDto initiatedBy,

        TaskAssignmentStatus status,

        Instant acceptedAt,

        Instant rejectedAt,

        Instant cancelledAt,

        Instant createdAt,

        Instant updatedAt

) {
}