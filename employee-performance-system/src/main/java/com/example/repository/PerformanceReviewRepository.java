package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.PerformanceReview;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
	@Query(value = "SELECT * FROM performance_review p WHERE p.employee_employee_id = :employeeId", nativeQuery = true)
	List<PerformanceReview> findByEmployeeId(@Param("employeeId") Long employeeId);

}
