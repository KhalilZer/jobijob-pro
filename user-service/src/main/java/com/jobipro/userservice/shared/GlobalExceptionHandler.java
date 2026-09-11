package com.jobipro.userservice.shared;

import com.jobipro.userservice.exceptions.ResourceAlreadyExist;
import com.jobipro.userservice.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<GlobalResponse<Void>> handleResourceNotFound(ResourceNotFound exception) {
        return GlobalResponse.error(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ResourceAlreadyExist.class)
    public ResponseEntity<GlobalResponse<Void>> handleResourceAlreadyExist(ResourceAlreadyExist exception) {
        return GlobalResponse.error(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }
}
