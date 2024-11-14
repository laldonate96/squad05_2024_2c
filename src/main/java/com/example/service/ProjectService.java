package com.example.service;

import com.example.model.Project;
import com.example.model.Task;
import com.example.repository.ProjectRepository;
import com.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(String id) {
        return projectRepository.findProjectById(id);
    }

    public List<Task> getProjectTasks(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public void save(Project project) {
        projectRepository.save(project);
    }
}