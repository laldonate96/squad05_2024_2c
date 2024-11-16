package com.taskmanager;

import com.taskmanager.model.TaskWork;
import com.taskmanager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
public class MainApp {

	@Autowired
	private TaskWorkService taskWorkService;

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
}
