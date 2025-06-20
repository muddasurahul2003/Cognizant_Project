package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.service.LoginService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
	@Autowired
    private LoginService employeeService;


    @PostMapping("/login")
    public Long login(@RequestParam String name, @RequestParam String password) {
        Long employeeId = employeeService.loginEmployee(name, password);
        if (employeeId != null) {
            return employeeId;
        } else {
            return (long) 0;
        }
    }

}
