package com.jobipro.taskservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ConfirmPhotosDto(
        @NotEmpty
        @Size(max = 5)
        List<@NotBlank String> objectKeys
) {
}
