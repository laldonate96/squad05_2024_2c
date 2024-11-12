package com.example.model;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long governmentId;

    private String name;

    public Employee() {}

    public Employee(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public Long getGovernmentId() { return this.governmentId; }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

