package com.jobipro.userservice.services;

import com.jobipro.userservice.abstracts.SectorServiceContract;
import com.jobipro.userservice.dtos.sector.SectorCreateRequest;
import com.jobipro.userservice.enteties.Sector;
import com.jobipro.userservice.mappers.SectorMapper;
import com.jobipro.userservice.repositories.SectorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorService implements SectorServiceContract {

    private final SectorMapper sectorMapper;
    private final SectorRepo sectorRepo;

    @Override
    public Sector create(SectorCreateRequest sectorCreateRequest) {
        Sector sector = sectorMapper.toEntity(sectorCreateRequest);

        return sectorRepo.save(sector);
    }

    public List<Sector> getAll() {
        return sectorRepo.findAll();
    }
}
