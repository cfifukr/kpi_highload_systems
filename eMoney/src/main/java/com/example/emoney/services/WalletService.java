package com.example.emoney.services;

import com.example.emoney.models.Wallet;
import com.example.emoney.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {


    private final WalletRepository walletRepository;


    @Transactional
    public void deleteWallet(Wallet wallet){
        walletRepository.delete(wallet);
    }
    @Transactional
    public Wallet saveWallet(Wallet wallet){
        return walletRepository.save(wallet);
    }



    @Transactional(readOnly = true)
    public Wallet findWalletById(Long id) throws RuntimeException{
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isEmpty()){
            throw new RuntimeException("No wallet with this id");
        }
        return wallet.get();
    }
}
