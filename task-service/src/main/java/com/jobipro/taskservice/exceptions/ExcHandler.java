package com.jobipro.taskservice.exceptions;

import com.jobipro.common.exceptions.ForbiddenException;
import com.jobipro.common.exceptions.ResourceAlreadyExist;
import com.jobipro.common.exceptions.ResourceNotFound;
import com.jobipro.common.response.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExcHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<GlobalResponse<Void>> handleResourceNotFound(ResourceNotFound ex) {
        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(WorkerNotAvailableException.class)
    public ResponseEntity<GlobalResponse<Void>> handleWorkerNotAvailable(WorkerNotAvailableException ex) {
        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(TaskNotOpenException.class)
    public ResponseEntity<GlobalResponse<Void>> handleTaskNotOpen(
            TaskNotOpenException ex
    ) {
        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(WorkerAlreadyInvitedException.class)
    public ResponseEntity<GlobalResponse<Void>> handleWorkerAlreadyInvited(
            WorkerAlreadyInvitedException ex
    ) {
        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<GlobalResponse<Void>> handleIllegalStateException(
            IllegalStateException ex
    ) {
        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<GlobalResponse<Void>> handleForbiddenException(
            ForbiddenException ex
    ) {
        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(CancellationNotAllowedException.class)
    public ResponseEntity<GlobalResponse<Void>> handleCancellationNotAllowedException(
            CancellationNotAllowedException ex
    ) {
        return GlobalResponse.error(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    
}
