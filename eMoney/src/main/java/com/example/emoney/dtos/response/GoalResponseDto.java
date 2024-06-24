package com.example.emoney.dtos.response;


import com.example.emoney.enums.Currency;
import com.example.emoney.models.Goal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GoalResponseDto {
    private Long id;
    private String goal;
    private Double moneyHave;
    private Double moneyNeed;
    private Currency currency;
    private String username;

    public static GoalResponseDto getDto(Goal originalGoal){
        GoalResponseDto responseDto = GoalResponseDto.builder()
                .id(originalGoal.getId())
                .goal(originalGoal.getGoal())
                .moneyHave(originalGoal.getMoneyHave())
                .moneyNeed(originalGoal.getMoneyNeed())
                .currency(originalGoal.getCurrency())
                .username(originalGoal.getUser().getUsername())
                .build();

        return responseDto;
    }

}
