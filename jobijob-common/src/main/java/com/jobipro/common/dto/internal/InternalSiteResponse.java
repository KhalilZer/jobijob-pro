package com.jobipro.common.dto.internal;

import com.jobipro.common.enums.SiteStatus;

public record InternalSiteResponse(
        String publicId,
        String companyPublicId,
        String managerUserId,
        String name,
        String description,
        String address,
        String city,
        String postalCode,
        String countryCode,
        Double latitude,
        Double longitude,
        SiteStatus status
) {
}