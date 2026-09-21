package com.jobipro.common.dto.internal;

import com.jobipro.common.UserRole;

public record InternalUserRequestDto(

        String firstName,
        String lastName,
        String email,
        String phone,
        UserRole role

) {
}
