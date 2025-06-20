package com.example.serviceImplementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.EmployeeProfile;
import com.example.entity.Feedback;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.repository.FeedbackRepository;
import com.example.service.FeedbackService;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Override
    public Feedback submitFeedback(Feedback feedback,long fid,long tid) {
    	EmployeeProfile from=employeeRepository.findById(fid).orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + fid));
    	feedback.setFromEmployee(from);
    	EmployeeProfile to=employeeRepository.findById(tid).orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + tid));
    	feedback.setToEmployee(to);
        logger.info("Feedback is created");
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbackForEmployee(Long employeeId) {
        return feedbackRepository.findAll().stream()
                .filter(f -> f.getToEmployee().getEmployeeId().equals(employeeId))
                .toList();
    }
}
