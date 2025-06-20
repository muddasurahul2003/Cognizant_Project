package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.EmployeeProfile;

public interface EmployeeRepository extends JpaRepository<EmployeeProfile, Long> {

	EmployeeProfile findByName(String employeeName);
}
