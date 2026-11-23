package com.jobipro.companyservice.abstracts;

import com.jobipro.common.dto.internal.InternalSiteResponse;
import com.jobipro.common.dto.internal.InternalUserRequestDto;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.companyservice.dtos.site.*;

import java.util.List;

public interface SiteContract {
    SiteResponseDto createSite(CreateSiteDto createSiteDto);

    List<SiteResponseDto> getAllSites();

    SiteResponseDto getOneSite(String publicId);

    SiteResponseDto updateSite(String publicId, UpdateSiteDto updateSiteDto);

    InternalUserResponseDto createManagerForSite(String sitePublicId, InternalUserRequestDto managerDto);

    InternalUserResponseDto updateManagerForSite(String sitePublicId, InternalUserRequestDto managerDto);

    InternalSiteResponse GetInternalSite(String publicId);
}
