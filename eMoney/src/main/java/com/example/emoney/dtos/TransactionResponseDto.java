package com.example.emoney.dtos;

import com.example.emoney.enums.Operation;
import com.example.emoney.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {
    private Long id;
    private Double money;
    private Operation operation;
    private String description;
    private LocalDateTime createdTime;

    public static TransactionResponseDto getDto(Transaction transaction){
        TransactionResponseDto dto = TransactionResponseDto.builder()
                .id(transaction.getId())
                .money(transaction.getMoney())
                .description(transaction.getDescription())
                .operation(transaction.getOperation())
                .createdTime(transaction.getCreatedTime())
                    .build();
        return dto;
    }

}
