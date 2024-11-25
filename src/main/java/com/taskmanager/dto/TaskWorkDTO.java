package com.taskmanager.dto;

import java.time.LocalDate;

public class TaskWorkDTO {
    private int id;
    private String taskId;
    private String resourceId;
    private String taskName;
    private String projectId;
    private String projectName;
    private LocalDate createdAt;
    private int hours;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getResourceId() { return resourceId; }
    public void setResourceId(String resourceId) { this.resourceId = resourceId; }
    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }
}