package com.example.service;

import java.util.List;

import com.example.entity.Feedback;

public interface FeedbackService {
    Feedback submitFeedback(Feedback feedback,long fid,long tid);
    List<Feedback> getFeedbackForEmployee(Long employeeId);
}
