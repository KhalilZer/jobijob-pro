package com.jobipro.companyservice.dtos.company;

import com.jobipro.companyservice.dtos.site.SiteResponseDto;

import java.util.List;

public record CompanyResponseDto(
        String publicId,
        List<String> sectorCodes,
        String website,
        String address,
        String city,
        String countryCode,
        Double longitude,
        Double latitude,
        String description,
        String logo,
        String phone,
        String email,
        List<SiteResponseDto> sites

) {
}
