package com.taskmanager.service;

import com.taskmanager.dto.ResourceDTO;
import com.taskmanager.model.TaskWork;
import com.taskmanager.repository.TaskWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectResourcesHoursService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final TaskWorkRepository taskWorkRepository;
    private final String TASKS_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/tareas-api/1.0.0/m/tareas";
    private final String RESOURCES_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/recursos-api/1.0.0/m/recursos";

    @Autowired
    public ProjectResourcesHoursService(TaskWorkRepository taskWorkRepository) {
        this.taskWorkRepository = taskWorkRepository;
    }

    public Map<String, Object> getResourcesMonthlyHours(String projectId, int year) {
        List<Map<String, Object>> allTasks = Arrays.asList(restTemplate.getForObject(TASKS_URL, Map[].class));
        List<ResourceDTO> allResources = Arrays.asList(restTemplate.getForObject(RESOURCES_URL, ResourceDTO[].class));

        Map<String, String> taskToResourceMap = allTasks.stream()
                .filter(task -> projectId.equals(task.get("proyectoId")))
                .collect(Collectors.toMap(
                        task -> (String) task.get("id"),
                        task -> (String) task.get("recursoId")
                ));

        Map<String, Map<Month, Integer>> resourceMonthlyHours = taskWorkRepository.findAll().stream()
                .filter(work -> work.getCreatedAt().getYear() == year && taskToResourceMap.containsKey(work.getTaskId()))
                .collect(Collectors.groupingBy(
                        work -> taskToResourceMap.get(work.getTaskId()),
                        Collectors.groupingBy(
                                work -> work.getCreatedAt().getMonth(),
                                Collectors.summingInt(TaskWork::getHours)
                        )
                ));

        List<Map<String, Object>> resources = allResources.stream()
                .filter(resource -> resourceMonthlyHours.containsKey(resource.getId()))
                .map(resource -> {
                    Map<Month, Integer> monthlyHours = resourceMonthlyHours.getOrDefault(resource.getId(), new EnumMap<>(Month.class));
                    int totalHours = monthlyHours.values().stream().mapToInt(Integer::intValue).sum();

                    Map<String, Object> resourceDetail = new HashMap<>();
                    resourceDetail.put("resourceId", resource.getId());
                    resourceDetail.put("nombre", resource.getNombre());
                    resourceDetail.put("apellido", resource.getApellido());
                    resourceDetail.put("dni", resource.getDni());
                    resourceDetail.put("monthlyHours", Arrays.stream(Month.values())
                            .collect(Collectors.toMap(
                                    month -> month.toString().toLowerCase(),
                                    month -> monthlyHours.getOrDefault(month, 0),
                                    (a, b) -> b,
                                    LinkedHashMap::new
                            )));
                    resourceDetail.put("totalHours", totalHours);
                    return resourceDetail;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("projectId", projectId);
        response.put("year", year);
        response.put("resources", resources);
        response.put("totalResources", resources.size());
        response.put("totalHours", resources.stream()
                .mapToInt(resource -> (Integer) resource.get("totalHours"))
                .sum());

        return response;
    }
}