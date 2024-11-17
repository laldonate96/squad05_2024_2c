package com.taskmanager.service;

import com.taskmanager.dto.ResourceDTO;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.exceptions.ProjectNotFoundException;
import com.taskmanager.exceptions.ResourceNotFoundException;
import com.taskmanager.exceptions.TaskNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String TASKS_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/tareas-api/1.0.0/m/tareas";
    private final String RESOURCES_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/recursos-api/1.0.1/m/recursos";


    public List<TaskDTO> getTaskByResourceId(String resourceId) {

        List<ResourceDTO> resources = Arrays.asList(
                restTemplate.getForObject(RESOURCES_URL, ResourceDTO[].class));

        boolean resourceExists = resources.stream()
                .anyMatch(resource -> resource.getId().equals(resourceId));

        if (!resourceExists) {
            throw new ResourceNotFoundException(resourceId);
        }

        List<TaskDTO> tasks = Arrays.asList(
                restTemplate.getForObject(TASKS_URL, TaskDTO[].class));

        Set<TaskDTO> tasksResourcesId = tasks.stream()
                .filter(task -> resourceId.equals(task.getRecursoId()))
                .collect(Collectors.toSet());

        return tasksResourcesId.stream()
                .collect(Collectors.toList());
    }

    public void taskExists(String taskId){
        List<TaskDTO> tasks = Arrays.asList(
                restTemplate.getForObject(TASKS_URL, TaskDTO[].class));

        boolean taskExists = tasks.stream()
                .anyMatch(task -> taskId.equals(task.getId()));

        if (!taskExists) {
            throw new TaskNotFoundException(taskId);
        }
    }
}
