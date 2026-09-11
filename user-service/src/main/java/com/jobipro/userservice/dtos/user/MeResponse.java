package com.jobipro.userservice.dtos.user;

public record MeResponse(
        String publicId,
        String email,
        String fullName
) {
}
