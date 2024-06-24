package com.example.emoney.dtos;

import com.example.emoney.enums.Operation;
import com.example.emoney.models.Transaction;
import com.example.emoney.models.Wallet;
import com.example.emoney.services.WalletService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDto {

    private Double money;
    private String operation;
    private String description;
    public Transaction getTransaction(Wallet wallet){
        Transaction transaction = new Transaction();
        transaction.setMoney(this.money);
        transaction.setOperation(Operation.valueOf(this.operation));
        transaction.setDescription(this.description);
        transaction.setWallet(wallet);

        return transaction;



    }
}
