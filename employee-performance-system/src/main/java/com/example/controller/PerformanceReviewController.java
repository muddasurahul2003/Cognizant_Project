package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.PerformanceReview;
import com.example.service.PerformanceReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService reviewService;

    @PostMapping("/{emp}/{man}")
    public PerformanceReview createReview(@RequestBody PerformanceReview review,@PathVariable Long emp, @PathVariable Long man) {
        return reviewService.createReview(review,emp,man);
    }

    @GetMapping("/employee/{employeeId}")
    public List<PerformanceReview> getReviewsByEmployee(@PathVariable Long employeeId) {
        return reviewService.getReviewsByEmployeeId(employeeId);
    }
    @GetMapping
    public List<PerformanceReview> getReviews() {
        return reviewService.getReviews();
    }
}
