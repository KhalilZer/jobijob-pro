package com.jobipro.taskservice.exceptions;

public class WorkerNotAvailableException extends RuntimeException {
    public WorkerNotAvailableException(String message) {
        super(message);
    }
}
