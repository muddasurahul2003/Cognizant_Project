package com.example.serviceImplementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.EmployeeProfile;
import com.example.repository.EmployeeRepository;
import com.example.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public LoginServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public Long loginEmployee(String employeeName, String password) {
        EmployeeProfile emp = employeeRepository.findByName(employeeName);
        if (emp != null && emp.getPassword().equals(password)) {
            logger.info("Login successful");
            return emp.getEmployeeId();
        }
        logger.info("Login unsuccessful");
        return null; 
    }

}
