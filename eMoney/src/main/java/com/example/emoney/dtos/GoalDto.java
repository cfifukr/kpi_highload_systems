package com.example.emoney.dtos;

import com.example.emoney.enums.Currency;
import com.example.emoney.models.Goal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto {
    private String goal;
    private Double money;
    private String currency;


    public Goal getGoal(){
        return new Goal(goal, money, Currency.valueOf(currency.toUpperCase().trim()));
    }
}
