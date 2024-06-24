package com.example.emoney.dtos;

import com.example.emoney.models.Goal;
import lombok.Data;

@Data
public class GoalUpdateDto {
    private Long id;
    private String goal;
    private Double money;
    private Double moneyHave;
    private Double moneyNeed;
    private Boolean isAccomplished;

    public Goal getUpdatedGoal(Goal goal){

        if (this.goal != null && !this.goal.trim().isEmpty()) {
            goal.setGoal(this.goal);
        }
        if (this.moneyHave != null) {
            goal.setMoneyHave(this.moneyHave);
        }

        if (this.money != null) {
            goal.setMoneyHave(goal.getMoneyHave() + this.money);
            if(goal.getMoneyHave() >= goal.getMoneyNeed()){
                goal.setIsAccomplished(true);
            }
        }
        if (this.moneyNeed != null) {
            goal.setMoneyNeed(this.moneyNeed);
        }


        return goal;
    }


}
