package com.jobipro.userservice.dtos.worker;

import com.jobipro.userservice.dtos.sector.SectorResponse;

import java.time.Instant;
import java.util.List;

public record WorkerResponse(
        String publicId,
        String address,
        String city,
        String postalCode,
        String countryCode,
        Double latitude,
        Double longitude,
        String description,
        Boolean available,
        List<SectorResponse> sectors,
        Instant createdAt
) {
}
