package com.jobipro.userservice.controllers;

import com.jobipro.userservice.dtos.sector.SectorCreateRequest;
import com.jobipro.userservice.enteties.Sector;
import com.jobipro.userservice.services.SectorService;
import com.jobipro.userservice.shared.GlobalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/sectors")
public class SectorController {

    private final SectorService sectorService;

    @PostMapping
    public ResponseEntity<GlobalResponse<Sector>> createSector(@Valid @RequestBody SectorCreateRequest sectorCreateRequest) {
        return GlobalResponse.success(
                sectorService.create(sectorCreateRequest),
                "Sector created successfully",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Sector>>> getAllSector() {
        return GlobalResponse.success(
                sectorService.getAll(),
                "Sectors retrieved successfully",
                HttpStatus.OK
        );
    }
}
