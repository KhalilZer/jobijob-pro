package com.jobipro.taskservice.clients;

import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.response.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", contextId = "userClient")
public interface UserClient {

    @GetMapping("/api/users/internal/me")
    GlobalResponse<InternalUserResponseDto> getConnectedUser();
}
