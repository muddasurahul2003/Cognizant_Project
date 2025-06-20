package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Feedback;
import com.example.service.FeedbackService;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/{fid}/{tid}")
    public Feedback submitFeedback(@RequestBody Feedback feedback, @PathVariable long fid, @PathVariable long tid) {
        return ((FeedbackService) feedbackService).submitFeedback(feedback,fid,tid);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Feedback> getFeedbackForEmployee(@PathVariable Long employeeId) {
        return ((FeedbackService) feedbackService).getFeedbackForEmployee(employeeId);
    }
}
