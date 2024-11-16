package com.taskmanager.model;

import com.taskmanager.exceptions.InvalidInput;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class TaskWork {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String taskId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    private int hours;

    public int getId() {
        return id;
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
        this.createdAt = createdAt;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
