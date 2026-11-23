package com.jobipro.userservice.services;

import com.jobipro.userservice.abstracts.SectorContract;
import com.jobipro.userservice.dtos.sector.SectorCreateRequest;
import com.jobipro.userservice.mappers.SectorMapper;
import com.jobipro.userservice.repositories.SectorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorService implements SectorContract {

    private final SectorMapper sectorMapper;
    private final SectorRepo sectorRepo;

    @Override
    public com.jobipro.userservice.enteties.Sector create(SectorCreateRequest sectorCreateRequest) {
        com.jobipro.userservice.enteties.Sector sector = sectorMapper.toEntity(sectorCreateRequest);

        return sectorRepo.save(sector);
    }

    public List<com.jobipro.userservice.enteties.Sector> getAll() {
        return sectorRepo.findAll();
    }
}
