package com.example.repository;

import com.example.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findEmployeeById(String id);

    Optional<Employee> findByGovernmentId(Long governmentId);

    @Override
    List<Employee> findAll();
}
