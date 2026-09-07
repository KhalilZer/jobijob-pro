package com.jobipro.userservice.dtos.user;

import com.jobipro.userservice.enums.UserType;

public record RegisterResponse(
        String email,
        String fullName,
        UserType UserType
) {
}
