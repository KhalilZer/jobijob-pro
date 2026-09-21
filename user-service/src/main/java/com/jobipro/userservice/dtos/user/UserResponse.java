package com.jobipro.userservice.dtos.user;

import com.jobipro.userservice.enums.UserStatus;

import java.time.Instant;
import java.time.LocalDate;

public record UserResponse(
        String publicId,
        String email,
        String fullName,
        LocalDate birthDate,
        String phone,
        String photo,
        String photoUrl,
        UserStatus status,
        Instant createdAt

) {
}
