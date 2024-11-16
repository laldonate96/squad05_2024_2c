package com.example.service;

import com.example.model.TaskWork;
import com.example.repository.TaskWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskWorkService{
    @Autowired
    private TaskWorkRepository taskWorkRepository;

    public TaskWork createTaskWork(TaskWork task) {
        return taskWorkRepository.save(task);
    }

    public List<TaskWork> getAllTaskWorks() {
        return taskWorkRepository.findAll();
    }

    public TaskWork getTaskWorkById(int id) {
        return taskWorkRepository.findTaskWorkById(id);
    }

    public void save(TaskWork task) {
        taskWorkRepository.save(task);
    }
}