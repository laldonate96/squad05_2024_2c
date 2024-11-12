package com.example.repository;

import com.example.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findEmployeeById(Long id);

    @Override
    List<Employee> findAll();
}
