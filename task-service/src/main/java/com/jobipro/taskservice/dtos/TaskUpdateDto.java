package com.jobipro.taskservice.dtos;

import com.jobipro.taskservice.enums.TaskStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record TaskUpdateDto(

        @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
        String title,

        @Size(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        @Future(message = "Start date must be in the future")
        Instant startDate,

        @Future(message = "End date must be in the future")
        Instant endDate,

        @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
        BigDecimal priceForPeriod,

        String photo,

        TaskStatus status

) {
}