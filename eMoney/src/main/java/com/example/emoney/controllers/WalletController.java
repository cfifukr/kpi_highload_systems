package com.example.emoney.controllers;

import com.example.emoney.dtos.UserReponseDto;
import com.example.emoney.dtos.WalletDto;
import com.example.emoney.dtos.WalletResponseDto;
import com.example.emoney.enums.Currency;
import com.example.emoney.exceptions.ExceptionDto;
import com.example.emoney.models.User;
import com.example.emoney.models.Wallet;
import com.example.emoney.services.JwtService;
import com.example.emoney.services.UserService;
import com.example.emoney.services.WalletService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
@CrossOrigin
@RequiredArgsConstructor
public class WalletController {
    private  final JwtService jwtService;
    private  final WalletService walletService;
    private  final UserService userService;


    @GetMapping
    public ResponseEntity<?> getWallets(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){

        String username = jwtService.extractUsername(authHeader.substring(7));

        User user = userService.findByUsername(username);
        List<WalletResponseDto> walletsDto = user.getWallets().stream().map(i -> WalletResponseDto.getDto(i)).toList();

        return ResponseEntity.ok(walletsDto);

    }

    @PostMapping
    public ResponseEntity<?> addWallets(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                        @RequestBody WalletDto walletDto){

        try{
            String username = jwtService.extractUsername(authHeader.substring(7));

            User user = userService.findByUsername(username);

            Wallet wallet = new Wallet();
            wallet.setName(walletDto.getName());
            wallet.setCurrency(Currency.valueOf(walletDto.getCurrency().toUpperCase().trim()));
            wallet.setBalance(0.00);
            wallet.setUser(user);

            user.addWallet(wallet);
            walletService.saveWallet(wallet);
            userService.saveUser(user);
            return ResponseEntity.ok(UserReponseDto.getDto(user));

        }catch (RuntimeException e){
            return ResponseEntity.ok(ResponseEntity.ok(new ExceptionDto(HttpStatus.BAD_REQUEST.value(),
                    "User can have only 5 wallets" )));
        }





    }

    @DeleteMapping ("/{wallet_id}")
    public ResponseEntity<?> deleteWalletById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                           @PathVariable Long wallet_id){
        String username = jwtService.extractUsername(authHeader.substring(7));
        Wallet wallet = walletService.findWalletById(wallet_id);

        if(wallet.isUsernameEqual(username)){
            walletService.deleteWallet(wallet);
            return ResponseEntity.ok("Deleted successfully");

        }
        return ResponseEntity.ok("Not deleted");

    }
    @GetMapping("/{wallet_id}")
    public ResponseEntity<?> getWalletById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                           @PathVariable Long wallet_id){

        String username = jwtService.extractUsername(authHeader.substring(7));

        Wallet wallet = walletService.findWalletById(wallet_id);

        if(wallet.isUsernameEqual(username)){
            return ResponseEntity.ok(WalletResponseDto.getDto(wallet));
        }
        return ResponseEntity.ok
                (new ExceptionDto(HttpStatus.NOT_ACCEPTABLE.value(),
                        "Wallet-username doesnt matches with jwt-username"));
    }
}
