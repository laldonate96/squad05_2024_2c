package com.example.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class TaskWork {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String taskId;

    private String createdAt;

    private int hours;

    public int getId() {
        return id;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getHours() {
        return hours;
    }
}
