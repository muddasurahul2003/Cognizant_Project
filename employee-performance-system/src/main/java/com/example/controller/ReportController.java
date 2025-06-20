package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.EmployeeProfile;
import com.example.entity.Report;
import com.example.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/{id}")
    public Report generateReport(@RequestBody Report report, @PathVariable long id) {
        return reportService.generateReport(report,id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Report> getReportsByEmployee(@PathVariable Long employeeId) {
        return reportService.getReportsByEmployeeId(employeeId);
    }
}
