package com.jobipro.taskservice.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record TaskCreateDto(

        @NotBlank(message = "Company ID is required")
        String companyId,

        String siteId,

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
        String title,

        @Size(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        @NotNull(message = "Start date is required")
        @Future(message = "Start date must be in the future")
        Instant startDate,

        @NotNull(message = "End date is required")
        @Future(message = "End date must be in the future")
        Instant endDate,

        @NotNull(message = "Price for period is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
        BigDecimal priceForPeriod,

        String photo
) {
}