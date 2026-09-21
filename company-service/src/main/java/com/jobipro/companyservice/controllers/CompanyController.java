package com.jobipro.companyservice.controllers;

import com.jobipro.common.response.GlobalResponse;
import com.jobipro.companyservice.dtos.company.CompanyResponseDto;
import com.jobipro.companyservice.dtos.company.CreateCompanyDto;
import com.jobipro.companyservice.dtos.company.UpdateCompanyDto;
import com.jobipro.companyservice.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;


    @PostMapping
    public ResponseEntity<GlobalResponse<CompanyResponseDto>> createCompany(
            @Valid @RequestBody CreateCompanyDto createCompanyDto
    ) {
        return GlobalResponse.success(
                companyService.create(createCompanyDto),
                "Company created successfully",
                HttpStatus.CREATED
        );
    }


    @GetMapping
    public ResponseEntity<GlobalResponse<List<CompanyResponseDto>>> getAllCompanies() {
        return GlobalResponse.success(
                companyService.getAllCompanies(),
                "Companies retrieved successfully",
                HttpStatus.OK
        );
    }


    @GetMapping("/{publicId}")
    public ResponseEntity<GlobalResponse<CompanyResponseDto>> getOneCompany(
            @PathVariable String publicId
    ) {
        return GlobalResponse.success(
                companyService.getOneCompany(publicId),
                "Company retrieved successfully",
                HttpStatus.OK
        );
    }


    @PatchMapping("/{publicId}")
    public ResponseEntity<GlobalResponse<CompanyResponseDto>> updateCompany(
            @PathVariable String publicId,
            @Valid @RequestBody UpdateCompanyDto updateCompanyDto
    ) {
        return GlobalResponse.success(
                companyService.update(publicId, updateCompanyDto),
                "Company updated successfully",
                HttpStatus.OK
        );
    }
}