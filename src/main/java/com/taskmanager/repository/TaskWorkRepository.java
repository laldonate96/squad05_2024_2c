package com.taskmanager.repository;

import com.taskmanager.model.TaskWork;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskWorkRepository extends CrudRepository<TaskWork, Integer> {
    TaskWork findTaskWorkById(int id);
    List<TaskWork> findAll();
    TaskWork save(TaskWork task);
}