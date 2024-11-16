package com.taskmanager.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TaskWork {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String taskId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private int hours;

    public int getId() {
        return id;
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getHours() {
        return hours;
    }
}
