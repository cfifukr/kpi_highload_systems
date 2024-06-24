package com.example.emoney.controllers;


import com.example.emoney.dtos.TransactionDto;
import com.example.emoney.dtos.TransactionResponseDto;
import com.example.emoney.enums.Operation;
import com.example.emoney.exceptions.ExceptionDto;
import com.example.emoney.models.Transaction;
import com.example.emoney.models.Wallet;
import com.example.emoney.services.JwtService;
import com.example.emoney.services.TransactionService;
import com.example.emoney.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final JwtService jwtService;
    private final TransactionService transactionService;
    private final WalletService walletService;

    @GetMapping("/{wallet_id}")
    public ResponseEntity<?> getTransByWallet(@RequestHeader (HttpHeaders.AUTHORIZATION) String authToken,
                                           @PathVariable Long wallet_id){

        Wallet wallet  = walletService.findWalletById(wallet_id);
        if(wallet.belongToAuthTokenUser(authToken, jwtService)) {
            List<Transaction> result = transactionService.getTransactionsByWallet(wallet);
            return ResponseEntity.ok(result.stream().map((i) -> TransactionResponseDto.getDto(i)).toList());

        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{wallet_id}")
    public ResponseEntity<?> addTransToWallet(@RequestHeader (HttpHeaders.AUTHORIZATION) String authToken,
                                              @RequestBody TransactionDto transactionDto,
                                              @PathVariable Long wallet_id){

        Wallet wallet  = walletService.findWalletById(wallet_id);
        Transaction transaction = transactionDto.getTransaction(wallet);
        if(Operation.valueOf(transactionDto.getOperation()).compareTo(Operation.IN) == 0){
            wallet.setBalance(wallet.getBalance() + transaction.getMoney());
        }
        if(Operation.valueOf(transactionDto.getOperation()).compareTo(Operation.OUT) == 0){
            wallet.setBalance(wallet.getBalance() - transaction.getMoney());
        }
        walletService.saveWallet(wallet);
        if(wallet.belongToAuthTokenUser(authToken, jwtService)) {
            return ResponseEntity.ok(TransactionResponseDto.getDto(transactionService.saveTransaction(transaction)));
        }
        return ResponseEntity.ok(new ExceptionDto(1, "U dont have access to this wallet"));
    }

}
