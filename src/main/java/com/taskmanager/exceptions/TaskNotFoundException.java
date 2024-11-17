package com.taskmanager.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String taskId) {
        super("No se encontró la tarea con ID: " + taskId);
    }
}