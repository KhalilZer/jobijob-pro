package com.jobipro.companyservice.mappers;


import com.jobipro.companyservice.dtos.site.CreateSiteDto;
import com.jobipro.companyservice.dtos.site.SiteResponseDto;
import com.jobipro.companyservice.dtos.site.UpdateSiteDto;
import com.jobipro.companyservice.entities.Site;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SiteMapper {


    Site toSiteEntity(CreateSiteDto createSiteDto);

    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "companyPublicId", source = "company.publicId")
    SiteResponseDto toResponse(Site site);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompanyFromDto(
            UpdateSiteDto updateCompanyDto,
            @MappingTarget Site company
    );
}
