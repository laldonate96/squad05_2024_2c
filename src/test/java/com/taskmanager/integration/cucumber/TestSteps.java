package com.taskmanager.integration.cucumber;

import com.taskmanager.model.TaskWork;
import com.taskmanager.service.TaskWorkService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class TestSteps {

    TaskWork taskWork;
    TaskWorkService taskWorkService = new TaskWorkService();

    @Given("una tarea con id \"([^\"]*)\"")
    public void taskWithId(String taskId){
        taskWork = new TaskWork();
        taskWork.setTaskId(taskId);
    }

    @When("se intentan cargar (\\d+) horas en la tarea")
    public void tryToChargeHours(int hours) {
        try {
            taskWork.setHours(hours);
            taskWorkService.createTaskWork(taskWork);
        } catch(RuntimeException ignored){

        }
    }

    @Then("las horas totales de la tarea con id \"([^\"]*)\" son (\\d+)$")
    public void lasHorasTotalesDeLaTareaSon(String taskId, int totalHours) {
        assertEquals(totalHours, taskWorkService.getHoursByTaskId(taskId));
    }

    @And("las horas del dia actual son (\\d+)$")
    public void lasHorasDelDiaActualSon(int hours) {
        assertEquals(hours, taskWork.getHours());
    }
}