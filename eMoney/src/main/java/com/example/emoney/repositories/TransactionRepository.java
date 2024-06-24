package com.example.emoney.repositories;

import com.example.emoney.enums.Operation;
import com.example.emoney.models.Transaction;
import com.example.emoney.models.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findTransactionsByWallet(Wallet wallet);


    @Query("SELECT x FROM Transaction x " +
            "WHERE x.wallet.user.username =:username " +
            "AND x.operation = :operation " +
            "AND x.createdTime BETWEEN :dateStart AND :dateEnd")
    List<Transaction> findTransactionsByPeriodAndUser(@Param("username") String username,
                                                     @Param("dateStart") LocalDateTime dateStart,
                                                     @Param("dateEnd") LocalDateTime dateEnd,
                                                      @Param("operation") Operation operation);



    @Query("SELECT x FROM Transaction x " +
            "WHERE x.wallet.user.username =:username " +
            "AND x.createdTime BETWEEN :dateStart AND :dateEnd")
    List<Transaction> findTransactionsByPeriodAndUser(@Param("username") String username,
                                                      @Param("dateStart") LocalDateTime dateStart,
                                                      @Param("dateEnd") LocalDateTime dateEnd);


    @Query("SELECT x FROM Transaction x " +
            "WHERE x.wallet.id =:walletId " +
            "AND x.createdTime BETWEEN :dateStart AND :dateEnd")
    Page<Transaction> findTransactionsByWalletAndDate(@Param("walletId") Long walletId,
                                                      @Param("dateStart") LocalDateTime dateStart,
                                                      @Param("dateEnd") LocalDateTime dateEnd,
                                                      Pageable pageable);
}
