package com.example.service;

import java.util.List;

import com.example.entity.Report;
public interface ReportService {
	
    Report generateReport(Report report,Long id);
    List<Report> getReportsByEmployeeId(Long employeeId);
}
