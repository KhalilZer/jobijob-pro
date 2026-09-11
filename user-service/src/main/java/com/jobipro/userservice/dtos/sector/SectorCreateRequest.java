package com.jobipro.userservice.dtos.sector;

public record SectorCreateRequest(
        String code,
        String name,
        String description
) {
}
