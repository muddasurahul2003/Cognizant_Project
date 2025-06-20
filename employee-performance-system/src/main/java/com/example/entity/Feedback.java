package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @ManyToOne
    @JoinColumn(name="fromemployeeid")
    @JsonIgnoreProperties
    private EmployeeProfile fromEmployee;

    @ManyToOne
    @JoinColumn(name="toemployeeid")
    @JsonIgnoreProperties
    private EmployeeProfile toEmployee;

    private String feedbackType;
    private String comments;
}
