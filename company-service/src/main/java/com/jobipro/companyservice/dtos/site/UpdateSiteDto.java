package com.jobipro.companyservice.dtos.site;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateSiteDto(
        
        @NotBlank
        @Size(max = 255)
        String name,

        @Size(max = 2000)
        String description,

        String address,

        String city,

        String postalCode,

        @Size(min = 2, max = 2)
        String countryCode,

        Double latitude,

        Double longitude,

        @Email
        String email,

        String phone

) {
}