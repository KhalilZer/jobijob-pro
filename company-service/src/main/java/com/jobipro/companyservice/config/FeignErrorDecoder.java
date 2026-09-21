package com.jobipro.companyservice.config;

import com.jobipro.common.exceptions.ResourceAlreadyExist;
import com.jobipro.common.exceptions.ResourceNotFound;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {

            case 404 -> new ResourceNotFound(
                    "Resource not found in remote service"
            );

            case 409 -> new ResourceAlreadyExist(
                    "User with this email already exists"
            );

            default -> defaultDecoder.decode(methodKey, response);
        };
    }
}