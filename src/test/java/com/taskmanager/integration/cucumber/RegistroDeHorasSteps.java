package com.taskmanager.integration.cucumber;

import com.taskmanager.dto.ResourceDTO;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.integration.cucumber.config.TestConfig;
import com.taskmanager.model.TaskWork;
import com.taskmanager.repository.TaskWorkRepository;
import com.taskmanager.service.ProjectResourcesService;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.TaskWorkService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@CucumberContextConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class RegistroDeHorasSteps {

    @Autowired
    private TaskWorkService taskWorkService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskWorkRepository taskWorkRepository;

    private TaskWork taskWork;
    private String errorMsj;

    @Given("una tarea con id {string}")
    public void taskWithId(String taskId) {
        taskWork = new TaskWork();
        taskWork.setTaskId(taskId);
        taskWork.setId(1);

        when(taskWorkRepository.save(any(TaskWork.class))).thenReturn(taskWork);
        when(taskWorkRepository.findTaskWorkById(1)).thenReturn(taskWork);

        List<TaskWork> taskWorks = new ArrayList<>();
        taskWorks.add(taskWork);
        when(taskWorkRepository.findByTaskId(taskId)).thenReturn(taskWorks);
    }

    @Given("una tarea con id {string} y {int} horas registradas en el dia {string}")
    public void unaTareaConConIdHorasTotalesRegistradasYHorasRegistradasEnElDia(String taskId, int hours, String date) {
        taskWork = new TaskWork();
        taskWork.setId(1);
        taskWork.setHours(hours);
        taskWork.setTaskId(taskId);
        taskWork.setCreatedAt(LocalDate.parse(date));

        when(taskWorkRepository.save(any(TaskWork.class))).thenReturn(taskWork);
        when(taskWorkRepository.findTaskWorkById(1)).thenReturn(taskWork);

        List<TaskWork> taskWorks = new ArrayList<>();
        taskWorks.add(taskWork);
        when(taskWorkRepository.findByTaskId(taskId)).thenReturn(taskWorks);

        taskWorkService.save(taskWork);
    }

    @When("se intentan cargar {int} horas en la tarea con id {string}")
    public void tryToChargeHours(int hours, String taskId) {
        try {
            taskService.taskExists(taskId);
            taskWork.setHours(hours);
            taskWorkService.createTaskWork(taskWork);
        } catch(RuntimeException e) {
            errorMsj = e.getMessage();
        }
    }

    @When("intento modificar a {int} las horas registradas de la tarea")
    public void intentoModificarALasHorasRegistradasDeLaTarea(int newHours) {
        try {
            taskWorkService.updateTaskWorkHoursById(taskWork.getId(), newHours);
        } catch(RuntimeException e) {
            errorMsj = e.getMessage();
        }
    }

    @Then("las horas totales de la tarea con id {string} son {int}")
    public void lasHorasTotalesDeLaTareaSon(String taskId, int totalHours) {
        assertEquals(totalHours, taskWorkService.getHoursByTaskId(taskId));
    }

    @Then("las horas cargadas son {int}")
    public void lasHorasDelDiaActualSon(int hours) {
        assertEquals(hours, taskWork.getHours());
    }

    @Then("la acción debería fallar debido a que {string}")
    public void laAccionDeberiaFallarDebidoAQue(String errorName) {
        assertEquals(errorMsj, errorName);
    }
}