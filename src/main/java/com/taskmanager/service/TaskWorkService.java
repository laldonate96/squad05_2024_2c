package com.taskmanager.service;

import com.taskmanager.dto.ProjectDTO;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.dto.TaskWorkDTO;
import com.taskmanager.model.TaskWork;
import com.taskmanager.repository.TaskWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskWorkService{
    @Autowired
    private TaskWorkRepository taskWorkRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RestTemplate restTemplate;

    private final String TASKS_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/tareas-api/1.0.0/m/tareas";
    private final String PROJECTS_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/proyectos-api/1.0.0/m/proyectos";

    public TaskWork createTaskWork(TaskWorkDTO request) {
        TaskWork taskWork = new TaskWork();
        taskWork.setTaskId(request.getTaskId());
        taskWork.setResourceId(request.getResourceId());
        taskWork.setHours(request.getHours());
        taskWork.setCreatedAt(request.getCreatedAt());

        return taskWorkRepository.save(taskWork);
    }

    public Collection<TaskWorkDTO> getAllTaskWorks() {
        List<TaskWork> taskWorks = taskWorkRepository.findAll();
        List<TaskDTO> tasks = Arrays.asList(
            restTemplate.getForObject(TASKS_URL, TaskDTO[].class));
        List<ProjectDTO> projects = Arrays.asList(
            restTemplate.getForObject(PROJECTS_URL, ProjectDTO[].class));

        return taskWorks.stream()
            .map(taskWork -> {
                TaskWorkDTO dto = new TaskWorkDTO();
                dto.setId(taskWork.getId());
                dto.setTaskId(taskWork.getTaskId());
                dto.setCreatedAt(taskWork.getCreatedAt());
                dto.setHours(taskWork.getHours());

                tasks.stream()
                    .filter(task -> task.getId().equals(taskWork.getTaskId()))
                    .findFirst()
                    .ifPresent(task -> {
                        dto.setTaskName(task.getNombre());
                        dto.setProjectId(task.getProyectoId());

                        projects.stream()
                            .filter(project -> project.getId().equals(task.getProyectoId()))
                            .findFirst()
                            .ifPresent(project -> dto.setProjectName(project.getNombre()));
                    });

                return dto;
            })
            .collect(Collectors.toList());
    }

    public Collection<TaskWorkDTO> getTaskWorksByResourceId(String resourceId) {
        List<TaskWork> taskWorks = taskWorkRepository.findByResourceId(resourceId);
        List<TaskDTO> tasks = Arrays.asList(restTemplate.getForObject(TASKS_URL, TaskDTO[].class));
        List<ProjectDTO> projects = Arrays.asList(restTemplate.getForObject(PROJECTS_URL, ProjectDTO[].class));

        return taskWorks.stream()
            .map(taskWork -> {
                TaskWorkDTO dto = new TaskWorkDTO();
                dto.setId(taskWork.getId());
                dto.setTaskId(taskWork.getTaskId());
                dto.setResourceId(taskWork.getResourceId());
                dto.setCreatedAt(taskWork.getCreatedAt());
                dto.setHours(taskWork.getHours());

                tasks.stream()
                    .filter(task -> task.getId().equals(taskWork.getTaskId()))
                    .findFirst()
                    .ifPresent(task -> {
                        dto.setTaskName(task.getNombre());
                        dto.setProjectId(task.getProyectoId());

                        projects.stream()
                            .filter(project -> project.getId().equals(task.getProyectoId()))
                            .findFirst()
                            .ifPresent(project -> dto.setProjectName(project.getNombre()));
                    });

                return dto;
            })
            .collect(Collectors.toList());
    }

    public TaskWorkDTO getTaskWorkById(int id) {
        TaskWork taskWork = taskWorkRepository.findTaskWorkById(id);

        List<TaskDTO> tasks = Arrays.asList(
            restTemplate.getForObject(TASKS_URL, TaskDTO[].class));
        List<ProjectDTO> projects = Arrays.asList(
            restTemplate.getForObject(PROJECTS_URL, ProjectDTO[].class));

        TaskWorkDTO dto = new TaskWorkDTO();
        dto.setId(taskWork.getId());
        dto.setTaskId(taskWork.getTaskId());
        dto.setCreatedAt(taskWork.getCreatedAt());
        dto.setHours(taskWork.getHours());

        tasks.stream()
            .filter(task -> task.getId().equals(taskWork.getTaskId()))
            .findFirst()
            .ifPresent(task -> {
                dto.setTaskName(task.getNombre());
                dto.setProjectId(task.getProyectoId());

                projects.stream()
                    .filter(project -> project.getId().equals(task.getProyectoId()))
                    .findFirst()
                    .ifPresent(project -> dto.setProjectName(project.getNombre()));
            });

        return dto;
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

    public void deleteTaskWorkById(int id){
        taskWorkRepository.deleteById(id);
    }
}
