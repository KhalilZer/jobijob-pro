package com.jobipro.userservice.dtos.user;

import com.jobipro.userservice.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginResponse(
        String publicId,
        String email,
        String fullName,
        UserType userType
) {
}
