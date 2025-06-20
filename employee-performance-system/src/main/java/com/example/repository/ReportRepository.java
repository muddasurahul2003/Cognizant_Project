package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
	
}
