package com.example.emoney.repositories;

import com.example.emoney.models.Wallet;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
