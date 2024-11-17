package com.taskmanager.integration.cucumber;

import com.taskmanager.dto.ResourceDTO;
import com.taskmanager.service.ProjectResourcesService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConsultaRecursosSteps {
    private String project;
    private List<ResourceDTO> resources;

    @Autowired
    private ProjectResourcesService projectResourcesService;

    @Given("un proyecto con id {string}")
    public void projectWithId(String projectId) {
        project = projectId;
    }

    @When("se intentan solicitar los recursos del proyecto")
    public void requestProjectResources() {
        ResourceDTO resource1 = new ResourceDTO();
        resource1.setId("1");
        resource1.setNombre("Resource 1");
        resource1.setApellido("Resource 1");
        resource1.setDni("12345678");
        resource1.setRolId("1");

        ResourceDTO resource2 = new ResourceDTO();
        resource2.setId("2");
        resource2.setNombre("Resource 2");
        resource2.setApellido("Resource 2");
        resource2.setDni("87654321");
        resource2.setRolId("2");

        resources = new ArrayList<>(List.of(resource1, resource2));

        when(projectResourcesService.getResourcesByProjectId(project))
            .thenReturn(resources);
    }

    @Then("se obtienen los recursos del proyecto")
    public void getProjectResources() {
        List<ResourceDTO> result = projectResourcesService.getResourcesByProjectId(project);
        assertEquals(resources, result);
    }

    @Then("la acción debería fallar debido a que el proyecto con id {string} no existe")
    public void projectNotFound(String projectId) {
        try {
            projectResourcesService.getResourcesByProjectId(projectId);
        } catch (Exception e) {
            assertEquals("No se encontró el proyecto con ID: " + projectId, e.getMessage());
        }
    }
}