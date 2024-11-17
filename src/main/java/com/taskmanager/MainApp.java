package com.taskmanager;

import com.taskmanager.dto.ResourceDTO;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.model.TaskWork;
import com.taskmanager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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

	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}


	@PostMapping("/task-work")
	@ResponseStatus(HttpStatus.CREATED)
	public TaskWork createTaskWork(@RequestBody TaskWork taskWork) {

        /*/if (taskWork.getHours()>0) {
            throw new TaskWorkHoursMustBeValid("Task Work hours must be greater than 0");
        }/*/
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

	@GetMapping("/resource/{resourceId}/task")
	public List<TaskDTO> getResourceTasks(@PathVariable String resourceId){
		return taskService.getTaskByResourceId(resourceId);
	}
}
