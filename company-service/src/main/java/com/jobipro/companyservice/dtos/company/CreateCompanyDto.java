package com.jobipro.companyservice.dtos.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateCompanyDto(

        @NotBlank
        String name,

        @NotBlank
        String vatNumber,

        @NotEmpty
        List<String> sectorCodes,


        @NotBlank
        String address,

        @NotBlank
        String postalCode,

        @NotBlank
        String city,

        @NotBlank
        String countryCode,

        @NotNull
        Double longitude,

        @NotNull
        Double latitude,
        String website,


        String description,

        String logo,

        @NotBlank
        String phone,

        @NotBlank
        @Email
        String email

) {
}
