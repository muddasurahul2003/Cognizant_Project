package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Goal;
import com.example.service.GoalService;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:3000")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/{id}")
    public Goal createGoal(@RequestBody Goal goal, @PathVariable Long id) {
        return goalService.createGoal(goal,id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Goal> getGoalsByEmployee(@PathVariable Long employeeId) {
        return goalService.getGoalsByEmployeeId(employeeId);
    }

    @PutMapping("/{goalId}")
    public Goal updateGoal(@PathVariable Long goalId, @RequestBody Goal goal) {
        return goalService.updateGoal(goalId, goal);
    }
}

