package com.example.serviceImplementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.EmployeeProfile;
import com.example.entity.Feedback;
import com.example.entity.PerformanceReview;
import com.example.entity.Report;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.repository.FeedbackRepository;
import com.example.repository.PerformanceReviewRepository;
import com.example.repository.ReportRepository;
import com.example.service.ReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public Report generateReport(Report report,Long id) {
    	
    	EmployeeProfile emp = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    	report.setEmployee(emp);

		List<PerformanceReview> rev = performanceReviewRepository.findByEmployeeId(id);
		report.setPerformanceSummary(rev);
	
	// Fetch only feedback for this employee
		List<Feedback> fb = feedbackRepository.findByEmployeeId(id);
		report.setFeedbackSummary(fb);
	
        return reportRepository.save(report);
    }

    @Override
    public List<Report> getReportsByEmployeeId(Long employeeId) {
        return reportRepository.findAll().stream()
                .filter(r -> r.getEmployee() != null && r.getEmployee().getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

}

