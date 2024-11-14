package com.example.service;

import com.example.dto.ExternalEmployeeDTO;
import com.example.dto.ExternalTaskDTO;
import com.example.model.Employee;
import com.example.model.Project;
import com.example.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TaskSyncService {
    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProjectService projectService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String EXTERNAL_API_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/tareas-api/1.0.0/m/tareas";

    public void importTasks() {
        ResponseEntity<List<ExternalTaskDTO>> response = restTemplate.exchange(
                EXTERNAL_API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ExternalTaskDTO>>() {}
        );

        List<ExternalTaskDTO> externalTasks = response.getBody();

        for (ExternalTaskDTO externalTask : externalTasks) {
            Task task = new Task();
            task.setId(externalTask.getId());
            task.setName(externalTask.getNombre());
            task.setDescription(externalTask.getDescripcion());
            Employee employee = employeeService.getEmployeeById(externalTask.getRecursoId());
            task.setEmployee(employee);
            Project project = projectService.getProjectById(externalTask.getProyectoId());
            task.setProject(project);

            Optional<Task> existingTask = Optional.ofNullable(taskService.getTaskById(task.getId()));
            if (existingTask.isEmpty()) {
                taskService.createTask(task);
            }
        }
    }
}
