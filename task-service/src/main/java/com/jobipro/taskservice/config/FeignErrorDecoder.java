
package com.jobipro.taskservice.config;

import com.jobipro.common.exceptions.ResourceAlreadyExist;
import com.jobipro.common.exceptions.ResourceNotFound;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger log =
            LoggerFactory.getLogger(FeignErrorDecoder.class);

    private final ObjectMapper objectMapper;

    public FeignErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();
        String body = "";

        try {
            if (response.body() != null) {
                body = Util.toString(
                        response.body().asReader(StandardCharsets.UTF_8)
                );
            }

            log.error(
                    "Feign call failed: method={}, status={}, body={}",
                    methodKey, status, body
            );

            String message = "Remote service error: " + status;

            if (!body.isBlank()) {
                try {
                    JsonNode json = objectMapper.readTree(body);

                    if (json != null && json.hasNonNull("message")) {
                        message = json.get("message").asText();
                    }
                } catch (Exception parseException) {
                    log.warn("Remote response is not valid JSON",
                            parseException);
                }
            }

            return switch (status) {
                case 404 -> new ResourceNotFound(message);
                case 409 -> new ResourceAlreadyExist(message);
                default -> new RuntimeException(
                        "HTTP " + status + ": " + message
                );
            };

        } catch (Exception e) {
            log.error(
                    "Failed to read Feign response: method={}, status={}",
                    methodKey, status, e
            );

            return new RuntimeException(
                    "Error reading remote service response", e
            );
        }
    }
}
