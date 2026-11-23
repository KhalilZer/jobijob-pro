package com.jobipro.userservice.abstracts;

import com.jobipro.userservice.dtos.sector.SectorCreateRequest;
import com.jobipro.userservice.enteties.Sector;

import java.util.List;

public interface SectorContract {
    Sector create(SectorCreateRequest sectorCreateRequest);

    List<Sector> getAll();
}
