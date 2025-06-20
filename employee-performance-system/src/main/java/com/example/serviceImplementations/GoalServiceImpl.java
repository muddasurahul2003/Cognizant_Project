package com.example.serviceImplementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.EmployeeProfile;
import com.example.entity.Goal;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.EmployeeRepository;
import com.example.repository.GoalRepository;
import com.example.service.GoalService;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    Logger logger = LoggerFactory.getLogger(GoalServiceImpl.class);
    
    @Override
    public Goal createGoal(Goal goal,Long id) {
    	EmployeeProfile emp = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: " + id));
    	goal.setEmployee(emp);
        logger.info("Goal is created");
        return goalRepository.save(goal);
    }

    @Override
    public List<Goal> getGoalsByEmployeeId(Long employeeId) {
        return goalRepository.findAll().stream()
                .filter(g -> g.getEmployee().getEmployeeId().equals(employeeId))
                .toList();
    }

    @Override
    public Goal updateGoal(Long goalId, Goal goal) {
        Goal existing = goalRepository.findById(goalId).orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: " + goalId));
        existing.setGoalDescription(goal.getGoalDescription());
        existing.setTargetDate(goal.getTargetDate());
        existing.setProgressStatus(goal.getProgressStatus());
        return goalRepository.save(existing);
    }
}
