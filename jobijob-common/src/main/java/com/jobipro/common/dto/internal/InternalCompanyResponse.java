package com.jobipro.common.dto.internal;

import com.jobipro.common.enums.CompanyStatus;

public record InternalCompanyResponse(
        String publicId,
        String ownerPublicId,
        String name,
        String description,
        String logo,
        String email,
        String phone,
        String website,
        String address,
        String city,
        String postalCode,
        String countryCode,
        CompanyStatus status
) {
}