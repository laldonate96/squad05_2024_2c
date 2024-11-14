package com.example.service;

import com.example.model.Employee;
import com.example.model.Task;
import com.example.repository.EmployeeRepository;
import com.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findEmployeeById(id);
    }

    public Optional<Employee> getEmployeeByGovernmentId(Long governmentId) {
        return employeeRepository.findByGovernmentId(governmentId);
    }

    public List<Task> getEmployeeTasks(String employeeId) {
        return taskRepository.findByEmployeeId(employeeId);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
