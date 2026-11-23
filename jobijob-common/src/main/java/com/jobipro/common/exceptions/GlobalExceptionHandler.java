package com.jobipro.common.exceptions;

import com.jobipro.common.response.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<GlobalResponse<Void>> handleForbidden(
            ForbiddenException ex
    ) {
        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }
}
