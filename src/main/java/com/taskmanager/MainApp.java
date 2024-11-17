package com.taskmanager;

import com.taskmanager.dto.ResourceDTO;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.model.TaskWork;
import com.taskmanager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
public class MainApp {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskWorkService taskWorkService;

	@Autowired
    private ProjectResourcesService projectResourcesService;

	@Autowired
	private ProjectHoursService projectHoursService;

	@Autowired
    private ProjectResourcesHoursService projectResourcesHoursService;

	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}


	@PostMapping("/task-work")
	@ResponseStatus(HttpStatus.CREATED)
	public TaskWork createTaskWork(@RequestBody TaskWork taskWork) {
		return taskWorkService.createTaskWork(taskWork);
	}

	@GetMapping("/task-work")
	public Collection<TaskWork> getAllTaskWorks() {
		return taskWorkService.getAllTaskWorks();
	}

	@GetMapping("/task-work/{id}")
	public TaskWork getTaskWork(@PathVariable int id) {
		return taskWorkService.getTaskWorkById(id);
	}

	@GetMapping("/task/{id}")
	public Collection<TaskWork> getTaskWorkByTaskId(@PathVariable String id){return taskWorkService.getTaskWorksByTaskId(id);}

	@PatchMapping("/task-work/{id}")
	public void chargeHours(@PathVariable int id, @RequestBody int hours){
		taskWorkService.updateTaskWorkHoursById(id, hours);
	}

	@GetMapping("/projects/{projectId}/resources")
    public List<ResourceDTO> getProjectResources(@PathVariable String projectId) {
        return projectResourcesService.getResourcesByProjectId(projectId);
    }

	@GetMapping("/projects/{projectId}/hours")
    public ResponseEntity<?> getProjectHours(@PathVariable String projectId) {
        try {
            Map<String, Object> projectHours = projectHoursService.getProjectTotalHours(projectId);
            return ResponseEntity.ok(projectHours);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch project hours");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

	@GetMapping("/projects/{projectId}/resources/hours/{year}")
	public ResponseEntity<?> getProjectResourcesMonthlyHours(
			@PathVariable String projectId,
			@PathVariable int year) {
		try {
			return ResponseEntity.ok(projectResourcesHoursService.getResourcesMonthlyHours(projectId, year));
		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Failed to fetch project resources monthly hours");
			error.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}

	@GetMapping("/resource/{resourceId}/task")
	public List<TaskDTO> getResourceTasks(@PathVariable String resourceId){
		return taskService.getTasksByResourceId(resourceId);
	}

	@PostMapping("/resource/{resourceId}/task-work")
	public List<TaskWork> getTaskWorksByResourceAndDate(@PathVariable String resourceId, @RequestBody LocalDate date){
		return taskWorkService.getTaskWorksByResourceAndDate(resourceId, date);
	}
}
