package com.example.service;

import com.example.dto.ExternalProjectDTO;
import com.example.model.Employee;
import com.example.model.Project;
import com.example.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectSyncService {
    @Autowired
    private ProjectService projectService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String EXTERNAL_API_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/proyectos-api/1.0.0/m/proyectos";

    public void importProjects() {
        ResponseEntity<List<ExternalProjectDTO>> response = restTemplate.exchange(
                EXTERNAL_API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ExternalProjectDTO>>() {}
        );

        List<ExternalProjectDTO> externalProjects = response.getBody();

        for (ExternalProjectDTO externalProject : externalProjects) {
            Project project = new Project();
            project.setId(externalProject.getId());
            project.setName(externalProject.getNombre());
            project.setDescription(externalProject.getDescripcion());

            Optional<Project> existingProject = Optional.ofNullable(projectService.getProjectById(project.getId()));
            if (existingProject.isEmpty()) {
                projectService.createProject(project);
            }
        }
    }
}
