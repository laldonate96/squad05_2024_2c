package com.taskmanager.service;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.model.TaskWork;
import com.taskmanager.repository.TaskWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskWorkService{
    @Autowired
    private TaskWorkRepository taskWorkRepository;

    @Autowired
    private TaskService taskService;

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

    public void updateTaskWorkHoursById(int id, int hours){
        TaskWork taskWork = taskWorkRepository.findTaskWorkById(id);
        taskWork.setHours(hours);
        taskWorkRepository.save(taskWork);
    }

    public List<TaskWork> getTaskWorksByTaskId(String taskId){
        return taskWorkRepository.findByTaskId(taskId);
    }

    public int getHoursByTaskId(String taskId){
        List<TaskWork> taskWorks = taskWorkRepository.findByTaskId(taskId);
        int cont=0;

        for(TaskWork taskWork: taskWorks){
            cont+= taskWork.getHours();
        }
        return cont;
    }

    public List<TaskWork> getTaskWorksByResourceAndDate(String resourceId, LocalDate date){
        List<TaskDTO> tasks = taskService.getTasksByResourceId(resourceId);
        List<TaskWork> taskWorks = new ArrayList<>();

        for (TaskDTO task: tasks){
            taskWorks.addAll(taskWorkRepository.findByTaskIdAndCreatedAtBetween(task.getId(), date, date.plusDays(7)));
        }

        return taskWorks;
    }
}
