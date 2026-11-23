package com.jobipro.companyservice.exceptions;

import com.jobipro.common.exceptions.ResourceAlreadyExist;
import com.jobipro.common.exceptions.ResourceNotFound;
import com.jobipro.common.response.GlobalResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalResponse<Void>> handleDataIntegrity(
            DataIntegrityViolationException ex
    ) {

        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ResourceAlreadyExist.class)
    public ResponseEntity<GlobalResponse<Void>> handleResourceAlreadyExist(
            ResourceAlreadyExist ex
    ) {

        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<GlobalResponse<Void>> handleResourceNotFound(ResourceNotFound ex) {
        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}
