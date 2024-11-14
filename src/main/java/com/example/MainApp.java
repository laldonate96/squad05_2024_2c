package com.example;

import com.example.dto.HoursDTO;
import com.example.exceptions.EmployeeAlreadyExistsException;
import com.example.exceptions.MissingFieldException;
import com.example.model.Employee;
import com.example.model.Project;
import com.example.model.Task;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
public class MainApp {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeSyncService employeeSyncService;
    @Autowired
    private ProjectSyncService projectSyncService;
    @Autowired
    private TaskSyncService taskSyncService;

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @PostMapping("/employees/sync")
    @ResponseStatus(HttpStatus.OK)
    public void importUsers() {
        employeeSyncService.importUsers();
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Validated @RequestBody Employee employee) {
        if (employee.getGovernmentId() == null) {
            throw new MissingFieldException("Government ID is required");
        }
        if (employeeService.getEmployeeByGovernmentId(employee.getGovernmentId()).isPresent()) {
            throw new EmployeeAlreadyExistsException("An employee with this government ID already exists.");
        }
        return employeeService.createEmployee(employee);
    }

    @GetMapping("/employees")
    public Collection<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/projects/sync")
    @ResponseStatus(HttpStatus.CREATED)
    public void importProjects() {
        projectSyncService.importProjects();
    }

    @GetMapping("/projects")
    public Collection<Project> getAllProjects() { return projectService.getAllProjects(); }

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable String id) { return projectService.getProjectById(id); }

    @GetMapping("/tasks")
    public Collection<Task> getAllTasks() { return taskService.getAllTasks(); }

    @PostMapping("/tasks/sync")
    @ResponseStatus(HttpStatus.CREATED)
    public void importTasks() {
        taskSyncService.importTasks();
    }

    @PatchMapping("/tasks/{taskId}/assign/{employeeId}")
    public Task assignTaskToEmployee(@PathVariable String taskId, @PathVariable String employeeId) {
        Task task = taskService.getTaskById(taskId);
        Employee employee = employeeService.getEmployeeById(employeeId);
        task.setEmployee(employee);
        taskService.save(task);
        return task;
    }

    @PatchMapping("/tasks/{id}")
    public Task addHoursToTask(@PathVariable String id, @RequestBody HoursDTO hoursDTO) {
        Task task = taskService.getTaskById(id);
        task.addHours(hoursDTO.getHours());
        taskService.save(task);
        return task;
    }
}
