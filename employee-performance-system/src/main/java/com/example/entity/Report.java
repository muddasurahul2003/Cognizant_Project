package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    private EmployeeProfile employee;

    private LocalDate reportDate;
    @ElementCollection
    private List<PerformanceReview> performanceSummary;
    @ElementCollection
    private List<Feedback> feedbackSummary;
    
}
