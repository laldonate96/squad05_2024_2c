package com.taskmanager.service;

import com.taskmanager.model.TaskWork;
import com.taskmanager.repository.TaskWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectHoursService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final TaskWorkRepository taskWorkRepository;
    private final String TASKS_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/tareas-api/1.0.0/m/tareas";

    @Autowired
    public ProjectHoursService(TaskWorkRepository taskWorkRepository) {
        this.taskWorkRepository = taskWorkRepository;
    }

    public Map<String, Object> getProjectTotalHours(String projectId) {
        // Get all tasks for the project
        List<Map<String, Object>> allTasks = Arrays.asList(
                restTemplate.getForObject(TASKS_URL, Map[].class));

        // Filter tasks for specific project
        List<String> projectTaskIds = allTasks.stream()
                .filter(task -> projectId.equals(task.get("proyectoId")))
                .map(task -> (String) task.get("id"))
                .collect(Collectors.toList());

        // Get all task work entries
        List<TaskWork> allTaskWorks = taskWorkRepository.findAll();

        // Calculate total hours for project tasks
        int totalHours = allTaskWorks.stream()
                .filter(taskWork -> projectTaskIds.contains(taskWork.getTaskId()))
                .mapToInt(TaskWork::getHours)
                .sum();

        // Get task-specific hours
        List<Map<String, Object>> taskDetails = allTasks.stream()
                .filter(task -> projectId.equals(task.get("proyectoId")))
                .map(task -> {
                    String taskId = (String) task.get("id");
                    String taskName = (String) task.get("nombre");
                    int taskHours = allTaskWorks.stream()
                            .filter(work -> taskId.equals(work.getTaskId()))
                            .mapToInt(TaskWork::getHours)
                            .sum();

                    Map<String, Object> detail = new HashMap<>();
                    detail.put("taskId", taskId);
                    detail.put("taskName", taskName);
                    detail.put("totalHours", taskHours);
                    return detail;
                })
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("projectId", projectId);
        result.put("totalHours", totalHours);
        result.put("taskDetails", taskDetails);

        return result;
    }
}