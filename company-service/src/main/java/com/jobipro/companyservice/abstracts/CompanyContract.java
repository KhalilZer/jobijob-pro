package com.jobipro.companyservice.abstracts;

import com.jobipro.companyservice.dtos.company.CompanyResponseDto;
import com.jobipro.companyservice.dtos.company.CreateCompanyDto;
import com.jobipro.companyservice.dtos.company.UpdateCompanyDto;

import java.util.List;

public interface CompanyContract {
    CompanyResponseDto create(CreateCompanyDto createCompanyDto);

    List<CompanyResponseDto> getAllCompanies();

    CompanyResponseDto getOneCompany(String publicId);

    CompanyResponseDto update(String publicId, UpdateCompanyDto updateCompanyDto);
}
