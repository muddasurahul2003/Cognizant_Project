package com.example.serviceImplementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.EmployeeProfile;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeService;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public EmployeeProfile createEmployee(EmployeeProfile employee) {
        logger.info("Employee is created");
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeProfile updateEmployee(Long id, EmployeeProfile employee) {
        EmployeeProfile existing = employeeRepository.findById(id).orElseThrow();
        existing.setName(employee.getName());
        existing.setDepartment(employee.getDepartment());
        existing.setRole(employee.getRole());
        existing.setContactDetails(employee.getContactDetails());
        logger.info("Employee is updated");
        return employeeRepository.save(existing);
    }

    @Override
    public EmployeeProfile getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public List<EmployeeProfile> getAllEmployees() {
        logger.info("All employees are retrieved");
        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
