package com.taskmanager.integration.cucumber;

import com.taskmanager.dto.ResourceDTO;
import com.taskmanager.model.TaskWork;
import com.taskmanager.repository.TaskWorkRepository;
import com.taskmanager.service.ProjectResourcesHoursService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ReportGenerationSteps {

    @Mock
    private TaskWorkRepository taskWorkRepository;

    @Mock
    private RestTemplate restTemplate;

    private ProjectResourcesHoursService projectResourcesHoursService;
    private Map<String, Object> reportResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        projectResourcesHoursService = new ProjectResourcesHoursService(taskWorkRepository);
        ReflectionTestUtils.setField(projectResourcesHoursService, "restTemplate", restTemplate);
    }

    @Given("un proyecto con id {string} con {int} horas cargadas en el año {int}")
    public void un_proyecto_con_id_con_horas_cargadas(String projectId, int hours, int year) {
        List<Map<String, Object>> mockTasks = Collections.singletonList(
            new HashMap<String, Object>() {{
                put("id", "task1");
                put("proyectoId", projectId);
                put("recursoId", "resource1");
                put("nombre", "Task 1");
            }}
        );

        List<ResourceDTO> mockResources = Collections.singletonList(
            new ResourceDTO() {{
                setId("resource1");
                setNombre("Test");
                setApellido("User");
                setDni("12345678");
            }}
        );

        when(restTemplate.getForObject(any(String.class), eq(Map[].class)))
            .thenReturn(mockTasks.toArray(new Map[0]));
        when(restTemplate.getForObject(any(String.class), eq(ResourceDTO[].class)))
            .thenReturn(mockResources.toArray(new ResourceDTO[0]));

        TaskWork taskWork = new TaskWork();
        taskWork.setTaskId("task1");
        taskWork.setHours(hours);
        taskWork.setCreatedAt(LocalDate.of(year, 1, 1));

        when(taskWorkRepository.findAll())
            .thenReturn(Collections.singletonList(taskWork));
    }

    @When("se intenta generar un reporte para el año {int}")
    public void se_intenta_generar_un_reporte(int year) {
        reportResult = projectResourcesHoursService.getResourcesMonthlyHours(
            "f635b4ca-c091-472c-8b5a-cb3086d1973",
            year
        );
    }

    @Then("el reporte debería contener {int} horas cargadas")
    public void el_reporte_deberia_contener_horas_cargadas(int expectedHours) {
        Integer totalHours = (Integer) reportResult.get("totalHours");
        assertEquals("The total hours should match", expectedHours, totalHours.intValue());
    }
}