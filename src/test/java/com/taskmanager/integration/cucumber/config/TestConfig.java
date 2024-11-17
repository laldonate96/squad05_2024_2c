package com.taskmanager.integration.cucumber.config;

import com.taskmanager.repository.TaskWorkRepository;
import com.taskmanager.service.ProjectResourcesService;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.TaskWorkService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public TaskService taskService() {
        return new TaskService();
    }

    @Bean
    public TaskWorkRepository taskWorkRepository() {
        return Mockito.mock(TaskWorkRepository.class);
    }

    @Bean
    public TaskWorkService taskWorkService() {
        return new TaskWorkService();
    }

    @Bean
    public ProjectResourcesService projectResourcesService() {
        return Mockito.mock(ProjectResourcesService.class);
    }
}