package com.jobipro.companyservice.services;

import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.exceptions.ResourceNotFound;
import com.jobipro.common.response.GlobalResponse;
import com.jobipro.companyservice.Clients.UserClient;
import com.jobipro.companyservice.abstracts.CompanyContract;
import com.jobipro.companyservice.dtos.company.CompanyResponseDto;
import com.jobipro.companyservice.dtos.company.CreateCompanyDto;
import com.jobipro.companyservice.dtos.company.UpdateCompanyDto;
import com.jobipro.companyservice.entities.Company;
import com.jobipro.companyservice.mappers.CompanyMapper;
import com.jobipro.companyservice.repositories.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements CompanyContract {


    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final UserClient userClient;

    @Override
    public CompanyResponseDto create(CreateCompanyDto createCompanyDto) {
        GlobalResponse<InternalUserResponseDto> response = userClient.getMe();

        InternalUserResponseDto user = response.getData();

        Company company = companyMapper.toCompanyEntity(createCompanyDto);

        company.setOwnerPublicId(user.publicId());

        return companyMapper.toCompanyResponse(companyRepo.save(company));

    }

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        List<Company> allCompanies = companyRepo.findAll();
        return allCompanies.stream().map(company -> companyMapper.toCompanyResponse(company)).toList();
    }

    @Override
    public CompanyResponseDto getOneCompany(String publicId) {
        Company company = companyRepo.findByPublicId(publicId).orElseThrow(() -> new ResourceNotFound("Company not found"));

        return companyMapper.toCompanyResponse(company);
    }

    @Override
    public CompanyResponseDto update(String publicId, UpdateCompanyDto updateCompanyDto) {
        Company company = companyRepo.findByPublicId(publicId).orElseThrow(() -> new ResourceNotFound("Company not found"));

        companyMapper.updateCompanyFromDto(updateCompanyDto, company);

        return companyMapper.toCompanyResponse(companyRepo.save(company));

    }
}
