package com.jobipro.companyservice.mappers;

import com.jobipro.companyservice.dtos.company.CompanyResponseDto;
import com.jobipro.companyservice.dtos.company.CreateCompanyDto;
import com.jobipro.companyservice.dtos.company.UpdateCompanyDto;
import com.jobipro.companyservice.entities.Company;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(source = "name", target = "name")
    Company toCompanyEntity(CreateCompanyDto createCompanyDto);

    CompanyResponseDto toCompanyResponse(Company company);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateCompanyFromDto(
            UpdateCompanyDto updateCompanyDto,
            @MappingTarget Company company
    );
}
