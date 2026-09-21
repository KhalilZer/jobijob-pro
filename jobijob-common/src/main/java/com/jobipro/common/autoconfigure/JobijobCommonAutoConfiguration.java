package com.jobipro.common.autoconfigure;

import com.jobipro.common.security.CustomAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ObjectMapper;

@AutoConfiguration
public class JobijobCommonAutoConfiguration {

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint(
            ObjectMapper objectMapper
    ) {
        return new CustomAuthenticationEntryPoint(objectMapper);
    }
}