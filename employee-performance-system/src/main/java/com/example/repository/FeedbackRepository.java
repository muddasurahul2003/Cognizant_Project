package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	@Query(value = "SELECT * FROM feedback f WHERE f.toemployeeid = :employeeId", nativeQuery = true)
	List<Feedback> findByEmployeeId(@Param("employeeId") Long employeeId);

}
