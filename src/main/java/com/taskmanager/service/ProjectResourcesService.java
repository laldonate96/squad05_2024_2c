package com.taskmanager.service;

import com.taskmanager.dto.ResourceDTO;
import com.taskmanager.exceptions.ProjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectResourcesService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String TASKS_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/tareas-api/1.0.0/m/tareas";
    private final String RESOURCES_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/recursos-api/1.0.1/m/recursos";

    public List<ResourceDTO> getResourcesByProjectId(String projectId) {
        List<Map<String, Object>> tasks = Arrays.asList(
                restTemplate.getForObject(TASKS_URL, Map[].class));

        boolean projectExists = tasks.stream()
                .anyMatch(task -> projectId.equals(task.get("proyectoId")));

        if (!projectExists) {
            throw new ProjectNotFoundException(projectId);
        }

        List<ResourceDTO> resources = Arrays.asList(
                restTemplate.getForObject(RESOURCES_URL, ResourceDTO[].class));

        Set<String> projectResourceIds = tasks.stream()
                .filter(task -> projectId.equals(task.get("proyectoId")))
                .map(task -> (String) task.get("recursoId"))
                .collect(Collectors.toSet());

        return resources.stream()
                .filter(resource -> projectResourceIds.contains(resource.getId()))
                .collect(Collectors.toList());
    }
}