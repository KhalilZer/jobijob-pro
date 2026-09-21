package com.jobipro.companyservice.dtos.site;

import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.companyservice.dtos.company.CompanyResponseDto;

public record SiteResponseDto(
        String publicId,
        InternalUserResponseDto manager,
        String name,
        String description,
        String address,
        String city,
        String postalCode,
        String countryCode,
        Double latitude,
        Double longitude,
        String companyPublicId
) {
}