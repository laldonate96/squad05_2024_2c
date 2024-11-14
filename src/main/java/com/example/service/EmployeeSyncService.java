package com.example.service;

import com.example.dto.ExternalEmployeeDTO;
import com.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSyncService {
    @Autowired
    private EmployeeService employeeService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String EXTERNAL_API_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/recursos-api/1.0.1/m/recursos";

    public void importUsers() {
        ResponseEntity<List<ExternalEmployeeDTO>> response = restTemplate.exchange(
                EXTERNAL_API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ExternalEmployeeDTO>>() {}
        );

        List<ExternalEmployeeDTO> externalUsers = response.getBody();

        for (ExternalEmployeeDTO externalUser : externalUsers) {
            Employee employee = new Employee();
            employee.setId(externalUser.getId());
            employee.setName(externalUser.getNombre() + " " + externalUser.getApellido());
            employee.setGovernmentId(Long.parseLong(externalUser.getDni()));
            employee.setRolId(externalUser.getRolId());

            Optional<Employee> existingEmployee = Optional.ofNullable(employeeService.getEmployeeById(employee.getId()));
            if (existingEmployee.isEmpty()) {
                employeeService.createEmployee(employee);
            }
        }
    }
}
