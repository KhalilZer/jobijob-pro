package com.jobipro.userservice.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateUserRequest(

        @NotEmpty
        List<Long> sectorsId,

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

        @NotNull
        Boolean isWorker

) {
}