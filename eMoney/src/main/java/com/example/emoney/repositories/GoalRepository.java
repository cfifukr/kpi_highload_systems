package com.example.emoney.repositories;

import com.example.emoney.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    Goal findGoalById(Long id);

    @Query("SELECT COALESCE(COUNT(x.id), 0) FROM Goal x WHERE x.user.username =:username")
    Integer totalGoalsFromUser(@Param("username") String username);

    @Query("SELECT x FROM Goal x WHERE x.user.username =:username ")
    List<Goal> findGoalsByUsername(@Param("username") String username);
}
