package com.jobipro.companyservice.dtos.company;

import jakarta.validation.constraints.Email;

import java.util.List;

public record UpdateCompanyDto(
        List<String> sectorCodes,

        String website,

        String address,

        String postalCode,

        String city,

        String countryCode,

        Double longitude,

        Double latitude,

        String description,

        String logo,

        String phone,

        @Email
        String email
) {

}
