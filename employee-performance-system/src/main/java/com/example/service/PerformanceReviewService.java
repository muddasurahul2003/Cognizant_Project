package com.example.service;

import java.util.List;

import com.example.entity.PerformanceReview;

public interface PerformanceReviewService {
    PerformanceReview createReview(PerformanceReview review, Long emp, Long man);
    List<PerformanceReview> getReviewsByEmployeeId(Long employeeId);
	List<PerformanceReview> getReviews();
}
