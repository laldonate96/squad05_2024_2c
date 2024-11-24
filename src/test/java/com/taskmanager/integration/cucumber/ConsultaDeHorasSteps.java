package com.taskmanager.integration.cucumber;

import com.taskmanager.dto.ResourceDTO;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.dto.TaskWorkDTO;
import com.taskmanager.model.TaskWork;
import com.taskmanager.repository.TaskWorkRepository;
import com.taskmanager.service.ProjectResourcesService;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.TaskWorkService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ConsultaDeHorasSteps {

    @Autowired
    TaskWorkService taskWorkService;

    @Autowired
    private TaskWorkRepository taskWorkRepository;

    @Autowired
    TaskService taskService;

    TaskWork taskWork1 = new TaskWork();
    TaskWork taskWork2 = new TaskWork();
    List<TaskWork> taskWorks = new ArrayList<>();
    String errorMsj;

    @Given("{int} horas cargadas en la tarea con id {string} en la fecha {string} y con {int} horas cargadas en la tarea con id {string} en la fecha {string}")
    public void anEmployeeWithTwoTaskWorks(int hours1, String taskId1, String date1, int hours2, String taskId2, String date2) {
        // Create first TaskWork using CreateTaskWorkRequest
        TaskWorkDTO request1 = new TaskWorkDTO();
        request1.setHours(hours1);
        request1.setTaskId(taskId1);
        request1.setCreatedAt(LocalDate.parse(date1));

        taskWork1.setHours(hours1);
        taskWork1.setTaskId(taskId1);
        taskWork1.setCreatedAt(LocalDate.parse(date1));

        when(taskWorkRepository.save(any(TaskWork.class))).thenReturn(taskWork1);
        when(taskWorkRepository.findTaskWorkById(1)).thenReturn(taskWork1);

        taskWorkService.createTaskWork(request1);

        // Create second TaskWork using CreateTaskWorkRequest
        TaskWorkDTO request2 = new TaskWorkDTO();
        request2.setHours(hours2);
        request2.setTaskId(taskId2);
        request2.setCreatedAt(LocalDate.parse(date2));

        taskWork2.setHours(hours2);
        taskWork2.setTaskId(taskId2);
        taskWork2.setCreatedAt(LocalDate.parse(date2));

        when(taskWorkRepository.save(any(TaskWork.class))).thenReturn(taskWork2);
        when(taskWorkRepository.findTaskWorkById(2)).thenReturn(taskWork2);

        taskWorkService.createTaskWork(request2);
    }

    @When("se intenta consultar las tareas asignadas en la semana de la fecha {string} del empleado con id {string}")
    public void taskInAWeek(String date, String resourceId) {
        List<TaskWork> list = new ArrayList<>();
        list.add(taskWork1);
        list.add(taskWork2);

        when(taskWorkRepository.findByTaskIdAndCreatedAtBetween("f635b4ca-c091-472c-8b5a-cb3086d1973", LocalDate.parse(date), LocalDate.parse(date).plusDays(7))).thenReturn(list);

        try {
            taskWorks = taskWorkService.getTaskWorksByResourceAndDate(resourceId, LocalDate.parse(date));
        } catch (RuntimeException e){
            errorMsj = e.getMessage();
        }
    }

    @Then("la cantidad de horas cargadas a la tarea con id {string} es {int}")
    public void hoursInATask(String taskId, int totalHours){
        int hours=0;
        for(TaskWork task: taskWorks){
            if (task.getTaskId().equals(taskId)){
                hours+=task.getHours();
            }
        }
        assertEquals(totalHours,hours);
    }

    @Then("la cantidad total trabajada por el empleado {string} en la semana del {string} es {int}")
    public void workingHoursInAWeek(String resourceId, String date, int hours){
        int hours2=0;
        for (TaskWork taskWork: taskWorkService.getTaskWorksByResourceAndDate(resourceId, LocalDate.parse(date))){
            hours2+=taskWork.getHours();
        }
        assertEquals(hours, hours2);
    }

    @Then("la busqueda debería fallar debido a que {string}")
    public void laAccionDeberiaFallarDebidoAQue(String errorName) {
        assertEquals(errorMsj, errorName);
    }
}
