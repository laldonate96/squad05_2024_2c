package com.taskmanager.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceId) {
        super("No se encontró el recurso con ID: " + resourceId);
    }
}