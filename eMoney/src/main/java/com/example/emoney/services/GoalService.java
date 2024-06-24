package com.example.emoney.services;


import com.example.emoney.models.Goal;
import com.example.emoney.models.User;
import com.example.emoney.repositories.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;

    @Transactional(readOnly = true)
    public Integer getTotalGoalsFromUser(String username){
        return goalRepository.totalGoalsFromUser(username);
    }

    @Transactional
    public Goal saveGoal(Goal goal){
        return goalRepository.save(goal);
    }

    @Transactional(readOnly = true)
    public List<Goal> getGoalsByUser(String username){
        return goalRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Page<Goal> getGoalsByUser(String username, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return goalRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Goal> getGoalsByUser(User user){
        return goalRepository.findGoalsByUsername(user.getUsername());
    }

    @Transactional
    public void deleteGoal(Goal goal){
        goalRepository.delete(goal);
    }

    @Transactional
    public void deleteGoal(Long id){
        goalRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Goal getGoalById(Long id){
        return goalRepository.findGoalById(id);
    }

    @Transactional
    public Goal comleteGoal(Long id) {
        Goal goal = getGoalById(id);
        goal.setIsAccomplished(true);
        return saveGoal(goal);
    }
}
