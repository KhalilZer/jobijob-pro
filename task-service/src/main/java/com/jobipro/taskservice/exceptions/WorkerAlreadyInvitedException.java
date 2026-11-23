package com.jobipro.taskservice.exceptions;

public class WorkerAlreadyInvitedException extends RuntimeException {
    public WorkerAlreadyInvitedException(String message) {
        super(message);
    }
}
