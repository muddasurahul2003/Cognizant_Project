package com.example.serviceImplementations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entity.EmployeeProfile;
import com.example.entity.PerformanceReview;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.repository.PerformanceReviewRepository;

@ExtendWith(MockitoExtension.class)
class PerformanceReviewServiceImplTest {

    @Mock
    private PerformanceReviewRepository reviewRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private PerformanceReviewServiceImpl performanceReviewService;

    private EmployeeProfile employee;
    private EmployeeProfile manager;
    private PerformanceReview review1;
    private PerformanceReview review2;

    @BeforeEach
    void setUp() {
        // Initialize common test data before each test
        employee = new EmployeeProfile();
        employee.setEmployeeId(1L);
        employee.setName("John");
  

        manager = new EmployeeProfile();
        manager.setEmployeeId(2L);
        manager.setName("Jane");
    

        review1 = new PerformanceReview();
        review1.setReviewId(101L);
        review1.setEmployee(employee);
        review1.setManager(manager);
        review1.setDate(LocalDate.of(2024, 1, 15));
        review1.setPerformanceScore(5);
        review1.setFeedback("Excellent performance, exceeded expectations.");

        review2 = new PerformanceReview();
        review2.setReviewId(102L);
        review2.setEmployee(employee);
        review2.setManager(manager);
        review2.setDate(LocalDate.of(2024, 7, 20));
        review2.setPerformanceScore(4);
        review2.setFeedback("Good performance, met all objectives.");
    }

    @Test
    void createReview_Success() {
    
        Long empId = employee.getEmployeeId();
        Long manId = manager.getEmployeeId();
        PerformanceReview newReview = new PerformanceReview(); 

       
        when(employeeRepository.findById(empId)).thenReturn(Optional.of(employee));
        when(employeeRepository.findById(manId)).thenReturn(Optional.of(manager));
      
        when(reviewRepository.save(any(PerformanceReview.class))).thenAnswer(invocation -> {
            PerformanceReview reviewToSave = invocation.getArgument(0);
            reviewToSave.setReviewId(1L); // Simulate ID generation
            return reviewToSave;
        });

       
        PerformanceReview createdReview = performanceReviewService.createReview(newReview, empId, manId);

      
        assertNotNull(createdReview);
        assertNotNull(createdReview.getReviewId()); // Ensure an ID was set
        assertEquals(employee.getEmployeeId(), createdReview.getEmployee().getEmployeeId());
        assertEquals(manager.getEmployeeId(), createdReview.getManager().getEmployeeId());
        assertEquals(employee.getName(), createdReview.getEmployee().getName());
        assertEquals(manager.getName(), createdReview.getManager().getName());

        
        verify(employeeRepository, times(1)).findById(empId);
        verify(employeeRepository, times(1)).findById(manId);
        verify(reviewRepository, times(1)).save(any(PerformanceReview.class));
    }

    @Test
    void createReview_EmployeeNotFound() {
       
        Long nonExistentEmpId = 99L; 
        Long manId = manager.getEmployeeId();
        PerformanceReview newReview = new PerformanceReview();

      
        when(employeeRepository.findById(nonExistentEmpId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            performanceReviewService.createReview(newReview, nonExistentEmpId, manId);
        });

        assertEquals("Employee not found with id: " + nonExistentEmpId, thrown.getMessage());

        // Verify interactions
        verify(employeeRepository, times(1)).findById(nonExistentEmpId);
        verify(employeeRepository, never()).findById(manId); // Manager should not be searched if employee is not found
        verify(reviewRepository, never()).save(any(PerformanceReview.class));
    }

    @Test
    void createReview_ManagerNotFound() {
        // Arrange
        Long empId = employee.getEmployeeId();
        Long nonExistentManId = 100L; // A non-existent manager ID
        PerformanceReview newReview = new PerformanceReview();

       
        when(employeeRepository.findById(empId)).thenReturn(Optional.of(employee));
        when(employeeRepository.findById(nonExistentManId)).thenReturn(Optional.empty());

      
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            performanceReviewService.createReview(newReview, empId, nonExistentManId);
        });

        assertEquals("Employee not found with id: " + nonExistentManId, thrown.getMessage());

       
        verify(employeeRepository, times(1)).findById(empId);
        verify(employeeRepository, times(1)).findById(nonExistentManId);
        verify(reviewRepository, never()).save(any(PerformanceReview.class));
    }

    @Test
    void getReviewsByEmployeeId_ReviewsFound() {
        
        Long targetEmployeeId = employee.getEmployeeId();
       
        List<PerformanceReview> allReviewsInRepo = Arrays.asList(review1, review2);


        when(reviewRepository.findAll()).thenReturn(allReviewsInRepo);

        
        List<PerformanceReview> reviews = performanceReviewService.getReviewsByEmployeeId(targetEmployeeId);

      
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertEquals(2, reviews.size());
        assertTrue(reviews.contains(review1));
        assertTrue(reviews.contains(review2));
        assertEquals(targetEmployeeId, reviews.get(0).getEmployee().getEmployeeId());
        assertEquals(targetEmployeeId, reviews.get(1).getEmployee().getEmployeeId());

       
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void getReviewsByEmployeeId_NoReviewsFoundForEmployee() {
       
        Long targetEmployeeId = 3L; 
       
        List<PerformanceReview> allReviewsInRepo = Arrays.asList(review1, review2);

        
        when(reviewRepository.findAll()).thenReturn(allReviewsInRepo);

    
        List<PerformanceReview> reviews = performanceReviewService.getReviewsByEmployeeId(targetEmployeeId);

       
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
        assertEquals(0, reviews.size());

       
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void getReviewsByEmployeeId_NoReviewsAtAllInRepo() {
        // Arrange
        Long targetEmployeeId = employee.getEmployeeId();

        // Mock repository behavior
        when(reviewRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<PerformanceReview> reviews = performanceReviewService.getReviewsByEmployeeId(targetEmployeeId);

        // Assert
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
        assertEquals(0, reviews.size());

        // Verify interactions
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void getReviewsByEmployeeId_ReviewsWithNullEmployeeField() {
        // Arrange
        PerformanceReview reviewWithNullEmployee = new PerformanceReview();
        reviewWithNullEmployee.setReviewId(103L);
        reviewWithNullEmployee.setManager(manager); // Employee is null here
        reviewWithNullEmployee.setDate(LocalDate.now());
        reviewWithNullEmployee.setPerformanceScore(3);
        reviewWithNullEmployee.setFeedback("Some feedback.");

        List<PerformanceReview> allReviewsInRepo = Arrays.asList(review1, reviewWithNullEmployee);

        // Mock repository behavior
        when(reviewRepository.findAll()).thenReturn(allReviewsInRepo);

        // Act
        List<PerformanceReview> reviews = performanceReviewService.getReviewsByEmployeeId(employee.getEmployeeId());

        // Assert: only review1 should be returned, as reviewWithNullEmployee will be filtered out
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertTrue(reviews.contains(review1));
        assertFalse(reviews.contains(reviewWithNullEmployee)); // This one should be filtered out

        // Verify interactions
        verify(reviewRepository, times(1)).findAll();
    }


    @Test
    void getReviews_ReviewsFound() {
        // Arrange
        List<PerformanceReview> allReviews = Arrays.asList(review1, review2);

        // Mock repository behavior
        when(reviewRepository.findAll()).thenReturn(allReviews);

        // Act
        List<PerformanceReview> reviews = performanceReviewService.getReviews();

        // Assert
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertEquals(2, reviews.size());
        assertTrue(reviews.contains(review1));
        assertTrue(reviews.contains(review2));

        // Verify interactions
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void getReviews_NoReviewsFound() {
        // Arrange
        // Mock repository behavior
        when(reviewRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<PerformanceReview> reviews = performanceReviewService.getReviews();

        // Assert
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
        assertEquals(0, reviews.size());

        // Verify interactions
        verify(reviewRepository, times(1)).findAll();
    }
}