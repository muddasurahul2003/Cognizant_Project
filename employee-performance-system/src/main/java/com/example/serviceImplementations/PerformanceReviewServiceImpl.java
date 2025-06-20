package com.example.serviceImplementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.EmployeeProfile;
import com.example.entity.PerformanceReview;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.repository.PerformanceReviewRepository;
import com.example.service.PerformanceReviewService;

import java.util.List;
import java.util.Optional;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    @Autowired
    private PerformanceReviewRepository reviewRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    Logger logger = LoggerFactory.getLogger(PerformanceReviewServiceImpl.class);


    @Override
    public PerformanceReview createReview(PerformanceReview review, Long emp, Long man) {
    	EmployeeProfile e = employeeRepository.findById(emp).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + emp));
    	review.setEmployee(e);
    	EmployeeProfile m = employeeRepository.findById(man).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + man));
    	review.setManager(m);
        logger.info("Performance review is created");
        return reviewRepository.save(review);
    }

    @Override
    public List<PerformanceReview> getReviewsByEmployeeId(Long employeeId) {
        return reviewRepository.findAll().stream()
                .filter(r -> Optional.ofNullable(r.getEmployee())
                        .map(emp -> emp.getEmployeeId())
                        .orElse(null) != null) 
                .filter(r -> r.getEmployee().getEmployeeId().equals(employeeId)) 
                .toList();
    }
    
    @Override
	public List<PerformanceReview> getReviews(){
        logger.info("All Reviews are retrieved");
    	return reviewRepository.findAll();
    }
}
