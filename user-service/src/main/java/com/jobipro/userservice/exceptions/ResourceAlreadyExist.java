package com.jobipro.userservice.exceptions;

public class ResourceAlreadyExist extends RuntimeException {
    public ResourceAlreadyExist(String message) {
        super(message);
    }
}
