package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private Long governmentId;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();

    private String name;

    private String rolId;

    public Employee() {}

    public Employee(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) { this.id = id; }

    public String getRolId() { return rolId; }

    public void setRolId(String rolId) { this.rolId = rolId; }

    public Long getGovernmentId() { return this.governmentId; }

    public void setGovernmentId(Long governmentId) { this.governmentId = governmentId; }

    public String getName() {
        return this.name;
    }

    public void setName(String name) { this.name = name; }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setEmployee(this);
    }
}

