package com.taskmanager.integration.cucumber;

import com.taskmanager.model.TaskWork;
import com.taskmanager.service.TaskWorkService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestSteps {

    TaskWork taskWork;
    TaskWorkService taskWorkService = new TaskWorkService();
    String errorMsj;

    @Given("una tarea con id \"([^\"]*)\"")
    public void taskWithId(String taskId){
        taskWork = new TaskWork();
        taskWork.setTaskId(taskId);
    }

    @Given("una tarea con id \"([^\"]*)\" y (-?\\d+) horas registradas en el dia \"([^\"]*)\"")
    public void unaTareaConConIdHorasTotalesRegistradasYHorasRegistradasEnElDia(String taskId, int hours, String date) {
        taskWork = new TaskWork();
        taskWork.setHours(hours);
        taskWork.setTaskId(taskId);
        taskWork.setCreatedAt(LocalDate.parse(date));

        taskWorkService.save(taskWork);
    }

    @When("se intentan cargar (-?\\d+) horas en la tarea")
    public void tryToChargeHours(int hours) {
        try {
            taskWork.setHours(hours);
            taskWorkService.createTaskWork(taskWork);
        } catch(RuntimeException e){
            errorMsj = e.getMessage();
        }
    }

    @When("intento modificar a (-?\\d+) las horas registradas de la tarea")
    public void intentoModificarALasHorasRegistradasDeLaTarea(int newHours) {
        try {
            taskWorkService.updateTaskWorkHoursById(taskWork.getId(), newHours);
        } catch(RuntimeException e){
            errorMsj = e.getMessage();
        }
    }

    @Then("las horas totales de la tarea con id \"([^\"]*)\" son (\\d+)$")
    public void lasHorasTotalesDeLaTareaSon(String taskId, int totalHours) {
        assertEquals(totalHours, taskWorkService.getHoursByTaskId(taskId));
    }

    @Then("las horas cargadas son (\\d+)$")
    public void lasHorasDelDiaActualSon(int hours) {
        assertEquals(hours, taskWork.getHours());
    }

    @Then("la acción debería fallar debido a que \"([^\"]*)\"$")
    public void laAccionDeberiaFallarDebidoAQue(String errorName) {
        assertEquals(errorMsj, errorName);
    }
}