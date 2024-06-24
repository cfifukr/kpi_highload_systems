package com.example.emoney.models;

import com.example.emoney.dtos.CreateTransactionDto;
import com.example.emoney.enums.Currency;

import com.example.emoney.enums.Operation;
import com.example.emoney.services.JwtService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double balance;

    private String name;

    @Column(nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name="user")
    private User user;


    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public  static Wallet createWallet(String inputName, Currency currency){
        Wallet wallet = new Wallet();
        wallet.setName(inputName);
        wallet.setCurrency(currency);
        wallet.setBalance(0.00);
        return wallet;

    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setWallet(this);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setWallet(null);
    }


    public synchronized Wallet makeTransaction(CreateTransactionDto transDto){
        Transaction transaction = new Transaction().builder()
                .money(transDto.getMoney())
                .description(transDto.getDescription())
                .wallet(this)
                .build();

        if(transDto.getOperation().toUpperCase().strip().compareTo("IN") == 0){
            transaction.setOperation(Operation.IN);
            this.balance += transDto.getMoney();
        }else{
            transaction.setOperation(Operation.OUT);
            this.balance -= transDto.getMoney();
        }

        this.addTransaction(transaction);
        return this;
    }

    public boolean belongToAuthTokenUser(String authToken, JwtService jwtService){
        String username = jwtService.extractUsername(authToken.substring(7));
        if(this.isUsernameEqual(username)){
            return true;
        }
        return false;
    }

    public String getUserame(){
        return this.getUser().getUsername();
    }

    public boolean isUsernameEqual(String usernameTwo){
        return  getUserame().compareTo(usernameTwo) == 0;
    }

    public String getStringCurrency(){
        return currency.getValue();
    }
}
