package com.jobipro.userservice.mappers;

import com.jobipro.userservice.dtos.sector.SectorCreateRequest;
import com.jobipro.userservice.enteties.Sector;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SectorMapper {

    Sector toEntity(SectorCreateRequest sectorCreateRequest);
}
