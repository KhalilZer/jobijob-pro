package com.jobipro.userservice.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UpdateUserRequest(
        List<Long> sectors,

        String address,
        String city,

        String postalCode,

        String countryCode,

        Double latitude,

        Double longitude,

        String fullName,

        LocalDate birthDate,

        String phone,

        String description,
        String photo

) {
}
