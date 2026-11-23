package com.jobipro.companyservice.Clients;

import com.jobipro.common.dto.internal.InternalUserRequestDto;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.response.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/internal/me")
    GlobalResponse<InternalUserResponseDto> getMe();

    @PostMapping("/api/users/internal/users")
    GlobalResponse<InternalUserResponseDto> createInternalUser(InternalUserRequestDto internalCreateUserDto);
}
