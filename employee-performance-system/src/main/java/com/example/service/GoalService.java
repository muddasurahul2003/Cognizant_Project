package com.example.service;

import java.util.List;

import com.example.entity.Goal;

public interface GoalService {
    Goal createGoal(Goal goal, Long id);
    List<Goal> getGoalsByEmployeeId(Long employeeId);
    Goal updateGoal(Long goalId, Goal goal);
}
