package com.jobipro.taskservice.dtos;

import com.jobipro.common.dto.internal.InternalCompanyResponse;
import com.jobipro.common.dto.internal.InternalSiteResponse;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.taskservice.dtos.assignment.AssignmentResponseDto;
import com.jobipro.taskservice.enums.TaskStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record TaskResponseDto(

        String publicId,
        String title,
        InternalUserResponseDto createdBy,
        InternalCompanyResponse company,
        InternalSiteResponse site,
        String description,
        Instant startDate,
        Instant endDate,
        BigDecimal priceForPeriod,
        List<String> photoUrls,
        TaskStatus status,
        List<AssignmentResponseDto> assignments,
        Instant createdAt,
        Instant updatedAt

) {
}