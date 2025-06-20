package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
