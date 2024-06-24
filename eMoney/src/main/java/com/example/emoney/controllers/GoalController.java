package com.example.emoney.controllers;


import com.example.emoney.dtos.GoalDto;
import com.example.emoney.dtos.pageable.GoalPageDto;
import com.example.emoney.dtos.response.GoalResponseDto;
import com.example.emoney.dtos.GoalUpdateDto;
import com.example.emoney.exceptions.ExceptionDto;
import com.example.emoney.models.Goal;
import com.example.emoney.models.User;
import com.example.emoney.services.GoalService;
import com.example.emoney.services.JwtService;
import com.example.emoney.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
@CrossOrigin
public class GoalController {
    private final GoalService goalService;
    private final UserService userService;
    private final JwtService jwtService;



    @GetMapping
    public ResponseEntity<?> getGoals(@RequestHeader (HttpHeaders.AUTHORIZATION) String authHeader,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size){

        String username = jwtService.extractUsernameFromAuthHeader(authHeader);
        Page<Goal> goals = goalService.getGoalsByUser(username, page, size);
        System.out.println(goals);
        Integer totalGoals = goalService.getTotalGoalsFromUser(username);
        GoalPageDto result = new GoalPageDto((long) totalGoals,
                Math.ceilDiv(totalGoals, size),
                page,
                goals.stream().map(i -> GoalResponseDto.getDto(i)).toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{goal_id}")
    public ResponseEntity<?> getGoalById(@RequestHeader (HttpHeaders.AUTHORIZATION) String authHeader,
                                      @PathVariable Long goal_id){
        Goal goal = goalService.getGoalById(goal_id);
        if(goalBelongTo(authHeader, goal)) {
            return ResponseEntity.ok(goal);
        }else{
            return ResponseEntity.ok(new ExceptionDto(HttpStatus.FORBIDDEN.value(),
                    "This goal belongs to another user"));
        }

    }

    @PostMapping
    public ResponseEntity<?> addGoal(@RequestHeader (HttpHeaders.AUTHORIZATION) String authHeader,
                                     @RequestBody GoalDto goalDto){
        String username = jwtService.extractUsernameFromAuthHeader(authHeader);
        User user = userService.findByUsername(username);
        Goal goal = goalDto.getGoal();
        user.addGoal(goal);
        return ResponseEntity.ok(GoalResponseDto.getDto(goalService.saveGoal(goal)));
    }

    @DeleteMapping("/{goal_id}")
    public ResponseEntity<?> deleteGoal (@RequestHeader (HttpHeaders.AUTHORIZATION) String authHeader,
                                         @PathVariable Long goal_id){
        Goal goal = goalService.getGoalById(goal_id);
        if(goalBelongTo(authHeader, goal)) {
            goalService.deleteGoal(goal);
            return ResponseEntity.ok("Deleted successfully");
        }else{
            return ResponseEntity.ok(new ExceptionDto(HttpStatus.FORBIDDEN.value(),
                    "This goal belongs to another user"));
        }
    }

    @PutMapping("/complete/{goal_id}")
    public ResponseEntity<?> completeGoal (@RequestHeader (HttpHeaders.AUTHORIZATION) String authHeader,
                                         @PathVariable Long goal_id){
        Goal goal = goalService.getGoalById(goal_id);
        if(goalBelongTo(authHeader, goal)) {

            return ResponseEntity.ok(GoalResponseDto.getDto(goalService.comleteGoal(goal_id)));
        }else{
            return ResponseEntity.ok(new ExceptionDto(HttpStatus.FORBIDDEN.value(),
                    "This goal belongs to another user"));
        }
    }


    @PutMapping
    public ResponseEntity<?> updateGoal (@RequestHeader (HttpHeaders.AUTHORIZATION) String authHeader,
                                         @RequestBody GoalUpdateDto goalUpdateDto){
        System.out.println(goalUpdateDto);
        if(goalUpdateDto.getId() == null && goalUpdateDto.getId()  == 0){
            return ResponseEntity.ok(new ExceptionDto(HttpStatus.FORBIDDEN.value(),
                    "Wrong request(No id)"));
        }
        Goal goal = goalService.getGoalById(goalUpdateDto.getId());


        if(goalBelongTo(authHeader, goal)) {
            Goal newGoal = goalUpdateDto.getUpdatedGoal(goal);

            return ResponseEntity.ok(GoalResponseDto.getDto(goalService.saveGoal(newGoal)));
        }else{
            return ResponseEntity.ok(new ExceptionDto(HttpStatus.FORBIDDEN.value(),
                    "This goal belongs to another user"));
        }
    }

    private boolean goalBelongTo(String authHeader, Goal goal){
        String username = jwtService.extractUsernameFromAuthHeader(authHeader);
         return goal.getUser().getUsername().compareTo(username) == 0;
    }



}
