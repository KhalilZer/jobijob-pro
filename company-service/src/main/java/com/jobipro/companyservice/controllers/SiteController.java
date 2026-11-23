package com.jobipro.companyservice.controllers;

import com.jobipro.common.dto.internal.InternalCompanyResponse;
import com.jobipro.common.dto.internal.InternalSiteResponse;
import com.jobipro.common.dto.internal.InternalUserRequestDto;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.response.GlobalResponse;
import com.jobipro.companyservice.dtos.site.CreateSiteDto;
import com.jobipro.companyservice.dtos.site.SiteResponseDto;
import com.jobipro.companyservice.dtos.site.UpdateSiteDto;
import com.jobipro.companyservice.services.SiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies/sites")
public class SiteController {

    private final SiteService siteService;


    @PostMapping
    public ResponseEntity<GlobalResponse<SiteResponseDto>> createSite(
            @Valid @RequestBody CreateSiteDto createSiteDto
    ) {
        return GlobalResponse.success(
                siteService.createSite(createSiteDto),
                "Site created successfully",
                HttpStatus.CREATED
        );
    }


    @GetMapping
    public ResponseEntity<GlobalResponse<List<SiteResponseDto>>> getAllSites() {
        return GlobalResponse.success(
                siteService.getAllSites(),
                "Sites retrieved successfully",
                HttpStatus.OK
        );
    }


    @GetMapping("/{publicId}")
    public ResponseEntity<GlobalResponse<SiteResponseDto>> getOneSite(
            @PathVariable String publicId
    ) {
        return GlobalResponse.success(
                siteService.getOneSite(publicId),
                "Site retrieved successfully",
                HttpStatus.OK
        );
    }


    @PatchMapping("/{publicId}")
    public ResponseEntity<GlobalResponse<SiteResponseDto>> updateSite(
            @PathVariable String publicId,
            @Valid @RequestBody UpdateSiteDto updateSiteDto
    ) {
        return GlobalResponse.success(
                siteService.updateSite(publicId, updateSiteDto),
                "Site updated successfully",
                HttpStatus.OK
        );
    }


    @PostMapping("/{sitePublicId}/manager")
    public ResponseEntity<GlobalResponse<InternalUserResponseDto>> createManagerForSite(
            @PathVariable String sitePublicId,
            @Valid @RequestBody InternalUserRequestDto managerDto
    ) {
        return GlobalResponse.success(
                siteService.createManagerForSite(sitePublicId, managerDto),
                "Site manager created successfully",
                HttpStatus.CREATED
        );
    }


    @PutMapping("/{sitePublicId}/manager")
    public ResponseEntity<GlobalResponse<InternalUserResponseDto>> updateManagerForSite(
            @PathVariable String sitePublicId,
            @Valid @RequestBody InternalUserRequestDto managerDto
    ) {
        return GlobalResponse.success(
                siteService.updateManagerForSite(sitePublicId, managerDto),
                "Site manager updated successfully",
                HttpStatus.OK
        );
    }


    //Client Export Endpoint
    @GetMapping("/internal/{publicId}")
    public ResponseEntity<GlobalResponse<InternalSiteResponse>> getInternalSite(@PathVariable String publicId) {
        return GlobalResponse.success(
                siteService.GetInternalSite(publicId),
                "Site retrieved successful",
                HttpStatus.OK
        );
    }

    @GetMapping("/internal/{publicId}/connectedUserIsManager")
    public ResponseEntity<GlobalResponse<Boolean>> connectedUserIsManager(@PathVariable String publicId

    ) {
        return GlobalResponse.success(
                siteService.connectedUserIsManager(publicId),
                "Check successful",
                HttpStatus.OK
        );
    }
}