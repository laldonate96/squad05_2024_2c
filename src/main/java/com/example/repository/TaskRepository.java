package com.example.repository;

import com.example.model.Task;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Task findTaskById(String id);
    List<Task> findByEmployeeId(String employeeId);
    List<Task> findByProjectId(String projectId);
    List<Task> findAll();
}