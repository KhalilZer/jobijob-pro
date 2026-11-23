package com.jobipro.taskservice.clients;

import com.jobipro.common.dto.internal.InternalSiteResponse;
import com.jobipro.common.response.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service", contextId = "siteClient")
public interface SiteClient {

    @GetMapping("/api/companies/sites/internal/{publicId}")
    GlobalResponse<InternalSiteResponse> getInternalSite(@PathVariable String publicId);

    @GetMapping("api/companies/internal/{publicId}/connectedUserIsManager")
    GlobalResponse<Boolean> connectedUserIsManager(@PathVariable String publicId);
}
