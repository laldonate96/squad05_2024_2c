package com.taskmanager.model;

import com.taskmanager.exceptions.InvalidInput;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class TaskWork {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String resourceId;

    private String taskId;

    @Column(name = "created_at")
    private LocalDate createdAt;

    private int hours;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        if (hours<=0){
            throw new InvalidInput("Las horas a cargar no pueden ser negativas");
        }
        this.hours = hours;
    }

    public void setCreatedAt(LocalDate createdAt) {
        if (createdAt == null) {
            this.createdAt = LocalDate.now();
        } else {
            this.createdAt = createdAt;
        }
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
