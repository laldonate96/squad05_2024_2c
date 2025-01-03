package com.taskmanager.repository;

import com.taskmanager.model.TaskWork;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskWorkRepository extends CrudRepository<TaskWork, Integer> {
    TaskWork findTaskWorkById(int id);
    List<TaskWork> findAll();
    TaskWork save(TaskWork task);
    List<TaskWork> findByTaskId(String taskId);
    List<TaskWork> findByResourceId(String resourceId);
    List<TaskWork> findByTaskIdAndCreatedAtBetween(String taskId, LocalDate dateStart, LocalDate dateFinish);
    List<TaskWork> findByResourceIdAndCreatedAtBetween(String resourceId, LocalDate dateStart, LocalDate dateFinish);
}