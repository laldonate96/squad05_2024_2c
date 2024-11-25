package com.taskmanager.service;

import com.taskmanager.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private RestTemplate restTemplate;

    private final String PROJECTS_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/proyectos-api/1.0.0/m/proyectos";

    public List<ProjectDTO> getAllProjects() {
        return Arrays.asList(restTemplate.getForObject(PROJECTS_URL, ProjectDTO[].class));
    }

    public ProjectDTO getProjectById(String projectId) {
        return getAllProjects().stream()
                .filter(project -> project.getId().equals(projectId))
                .findFirst()
                .orElse(null);
    }
}