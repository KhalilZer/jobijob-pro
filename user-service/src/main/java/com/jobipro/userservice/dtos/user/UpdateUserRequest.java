package com.jobipro.userservice.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UpdateUserRequest(
        @NotEmpty
        List<Long> sectors,

        @NotBlank
        String city,

        @NotBlank
        String postalCode,

        @NotBlank
        String countryCode,

        @NotNull
        Double latitude,

        @NotNull
        Double longitude,

        @NotBlank
        String fullName,

        @NotNull
        LocalDate birthDate,

        @NotBlank
        String phone
) {
}
