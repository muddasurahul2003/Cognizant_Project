package com.example.service;

import java.util.List;

import com.example.entity.EmployeeProfile;

public interface EmployeeService {
    EmployeeProfile createEmployee(EmployeeProfile employee);
    EmployeeProfile updateEmployee(Long id, EmployeeProfile employee);
    EmployeeProfile getEmployeeById(Long id);
    List<EmployeeProfile> getAllEmployees();
    void deleteEmployee(Long id);
}
