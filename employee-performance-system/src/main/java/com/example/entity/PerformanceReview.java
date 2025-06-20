package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @ManyToOne
    private EmployeeProfile employee;
    @ManyToOne
    private EmployeeProfile manager;
    
    private LocalDate date;
    private int performanceScore;
    private String feedback;
}
