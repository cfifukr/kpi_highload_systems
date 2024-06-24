package com.example.emoney.dtos.stat;

import com.example.emoney.dtos.TransactionResponseDto;
import com.example.emoney.dtos.WalletResponseDto;
import com.example.emoney.dtos.pageable.TransactionPageDto;
import com.example.emoney.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WalletStatDto {
    private WalletResponseDto walletDto;

    private TransactionPageDto transactionsDto;



    public WalletStatDto(WalletResponseDto walletDto, TransactionPageDto transactionsDto) {
        this.walletDto = walletDto;
        this.transactionsDto = transactionsDto;
    }


}
