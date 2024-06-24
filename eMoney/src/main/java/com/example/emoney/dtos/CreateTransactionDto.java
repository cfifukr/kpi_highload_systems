package com.example.emoney.dtos;


import com.example.emoney.models.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDto {
    private Double money;

    private String operation;

    private String description;
}
