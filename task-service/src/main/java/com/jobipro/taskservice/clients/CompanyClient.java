package com.jobipro.taskservice.clients;


import com.jobipro.common.dto.internal.InternalCompanyResponse;
import com.jobipro.common.response.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service", contextId = "companyClient")
public interface CompanyClient {

    @GetMapping("/api/companies/internal/{publicId}")
    GlobalResponse<InternalCompanyResponse> getInternalCompany(@PathVariable String publicId);

    @GetMapping("api/companies/internal/{publicId}/connectedUserIsOwner")
    GlobalResponse<Boolean> connectedUserIsOwner(@PathVariable String publicId);


}
