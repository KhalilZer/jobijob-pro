package com.jobipro.companyservice.services;

import com.jobipro.common.dto.internal.InternalUserRequestDto;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.exceptions.ResourceAlreadyExist;
import com.jobipro.common.exceptions.ResourceNotFound;
import com.jobipro.common.response.GlobalResponse;
import com.jobipro.companyservice.Clients.UserClient;
import com.jobipro.companyservice.abstracts.SiteContract;
import com.jobipro.companyservice.dtos.site.*;
import com.jobipro.companyservice.entities.Company;
import com.jobipro.companyservice.entities.Site;
import com.jobipro.companyservice.mappers.SiteMapper;
import com.jobipro.companyservice.repositories.CompanyRepo;
import com.jobipro.companyservice.repositories.SiteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteService implements SiteContract {

    private final SiteRepo siteRepo;
    private final SiteMapper siteMapper;
    private final UserClient userClient;
    private final CompanyRepo companyRepo;

    @Override
    public SiteResponseDto createSite(CreateSiteDto createSiteDto) {
        if (siteRepo.existsNormalized(
                createSiteDto.name(),
                createSiteDto.companyPublicId(),
                createSiteDto.address(),
                createSiteDto.city(),
                createSiteDto.countryCode(),
                createSiteDto.postalCode(),
                createSiteDto.latitude(),
                createSiteDto.longitude())) {
            throw new ResourceAlreadyExist("Site Already Exist with the same data");
        }

        Company company = companyRepo
                .findByPublicId(createSiteDto.companyPublicId())
                .orElseThrow(() -> new ResourceNotFound("Company not found"));

        Site site = siteMapper.toSiteEntity(createSiteDto);
        site.setCompany(company);
        return siteMapper.toResponse(siteRepo.save(site));
    }

    @Override
    public List<SiteResponseDto> getAllSites() {
        return siteRepo.findAll().stream().map(
                site -> siteMapper.toResponse(site)).toList();
    }

    @Override
    public SiteResponseDto getOneSite(String publicId) {
        Site site = siteRepo.findByPublicId(publicId).orElseThrow(() -> new ResourceNotFound("Site not found"));
        return siteMapper.toResponse(site);
    }

    @Override
    public SiteResponseDto updateSite(String publicId, UpdateSiteDto updateSiteDto) {
        Site site = siteRepo.findByPublicId(publicId).orElseThrow(() -> new ResourceNotFound("Site not found"));

        siteMapper.updateCompanyFromDto(updateSiteDto, site);

        return siteMapper.toResponse(site);

    }

    @Override
    public InternalUserResponseDto createManagerForSite(String sitePublicId, InternalUserRequestDto managerDto) {

        Site site = siteRepo.findByPublicId(sitePublicId).orElseThrow(() -> new ResourceNotFound("Site not found"));

        if (site.getManagerUserId() != null && !site.getManagerUserId().isBlank()) {
            throw new ResourceAlreadyExist("Site already has a manager");
        }

        GlobalResponse<InternalUserResponseDto> createdUser = userClient.createInternalUser(managerDto);
        site.setManagerUserId(createdUser.getData().publicId());
        siteRepo.save(site);

        return createdUser.getData();

    }

    @Override
    public InternalUserResponseDto updateManagerForSite(String sitePublicId, InternalUserRequestDto managerDto) {
        return null;
    }
}
