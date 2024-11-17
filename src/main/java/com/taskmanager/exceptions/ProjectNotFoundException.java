package com.taskmanager.exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String projectId) {
        super("No se encontró el proyecto con ID: " + projectId);
    }
}
