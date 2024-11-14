package com.example.repository;

import com.example.model.Project;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findProjectById(String id);
    List<Project> findAll();
}