package com.example.emoney.controllers;

import com.example.emoney.dtos.TransactionResponseDto;
import com.example.emoney.dtos.WalletResponseDto;
import com.example.emoney.dtos.pageable.TransactionPageDto;
import com.example.emoney.dtos.stat.WalletStatDto;
import com.example.emoney.enums.Operation;
import com.example.emoney.exceptions.ExceptionDto;
import com.example.emoney.models.Transaction;
import com.example.emoney.models.Wallet;
import com.example.emoney.services.JwtService;
import com.example.emoney.services.TransactionService;
import com.example.emoney.services.UserService;
import com.example.emoney.services.WalletService;
import com.example.emoney.utils.Stat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/v1/stat")
@RequiredArgsConstructor
@CrossOrigin
public class StatController {
    private final JwtService jwtService;
    private final UserService userService;
    private final WalletService walletService;
    private final TransactionService transactionService;

    // EXPECTED DATE FORMAT - (YYYY_MM_DD) 2024_11_25 or   (YYYY-MM-DD) 2024-11-25

    @GetMapping("/expenses/{dateStart}-{dateEnd}")
    public ResponseEntity<?> getExpensesPeriod(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
                                               @PathVariable String dateStart,
                                               @PathVariable String dateEnd){
        String username = jwtService.extractUsername(authToken.substring(7));

        LocalDate start = LocalDate.parse(dateStart.trim().replace("_", "-"));
        LocalDate end = LocalDate.parse(dateEnd.trim().replace("_", "-"));

        List<Transaction> transactions =  transactionService.getTransactionsByPeriod(username, start, end, Operation.OUT);

        Map<String, Double> map = Stat.getStatByDays(transactions, start, end);

        return ResponseEntity.ok(map);
    }


    @GetMapping("/incomes/{dateStart}-{dateEnd}")
    public ResponseEntity<?> getIncomePeriod(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
                                               @PathVariable String dateStart,
                                               @PathVariable String dateEnd){
        String username = jwtService.extractUsernameFromAuthHeader(authToken);

        LocalDate start = LocalDate.parse(dateStart.trim().replace("_", "-"));
        LocalDate end = LocalDate.parse(dateEnd.trim().replace("_", "-"));

        List<Transaction> transactions =  transactionService.getTransactionsByPeriod(username, start, end, Operation.IN);

        Map<String, Double> map = Stat.getStatByDays(transactions, start, end);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<?> getWalletStat(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
                                             @PathVariable Long walletId,
                                             @RequestParam String dateStart,
                                             @RequestParam String dateEnd,
                                           @RequestParam(defaultValue = "9") int size,
                                           @RequestParam(defaultValue = "0") int page){


        String username = jwtService.extractUsernameFromAuthHeader(authToken);
        Wallet wallet = walletService.findWalletById(walletId);
        LocalDate start = LocalDate.parse(dateStart.trim().replace("_", "-"));
        LocalDate end = LocalDate.parse(dateEnd.trim().replace("_", "-"));
        int totalTrans = wallet.getTransactions().size();

        //if wallet doesnt belong to user
        if(wallet.getUserame().compareTo(username) != 0){
            return ResponseEntity.ok(
                    new ExceptionDto(HttpStatus.FORBIDDEN.value(),
                    "Wallet doesnt belong to his user")
            );
        }

        //get transactions by date and user
        List<Transaction> transactions =  transactionService.getTransactionsByWalletAndDate(
                wallet,
                start,
                end,
                PageRequest.of(page, size)).stream().toList();

        TransactionPageDto transactionPageDto = new TransactionPageDto(
                (long) totalTrans,
                Math.ceilDiv(totalTrans, size),
                page,
                transactions.stream().map(i -> TransactionResponseDto.getDto(i)).toList());


        WalletStatDto result = new WalletStatDto(
                WalletResponseDto.getDto(wallet),
                transactionPageDto

        );


        return ResponseEntity.ok(result);
    }



//       @GetMapping
//        public ResponseEntity<?> getGeneralStat(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken){
//            String username = jwtService.extractUsernameFromAuthHeader(authToken);
//            User user = userService.findByUsername(username);
//

}
