package com.jobipro.taskservice.exceptions;

public class TaskNotOpenException extends RuntimeException {
    public TaskNotOpenException(String message) {
        super(message);
    }
}
