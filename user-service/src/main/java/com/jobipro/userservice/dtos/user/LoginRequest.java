package com.jobipro.userservice.dtos.user;

import com.jobipro.userservice.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank @Email
        String email,

        @NotBlank @Size(min = 6, max = 30, message = "Password must be between 6 and 30 chars")
        String password,
        
        @NotBlank
        String keycloakId
) {
}
