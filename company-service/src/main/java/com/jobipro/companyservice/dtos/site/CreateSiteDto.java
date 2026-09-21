package com.jobipro.companyservice.dtos.site;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSiteDto(

        @NotBlank
        String companyPublicId,

        @NotBlank
        @Size(max = 255)
        String name,

        @Size(max = 2000)
        String description,

        @NotBlank
        String address,

        @NotBlank
        String city,

        @NotBlank
        String postalCode,

        @NotBlank
        @Size(min = 2, max = 2)
        String countryCode,

        Double latitude,

        Double longitude

) {
}