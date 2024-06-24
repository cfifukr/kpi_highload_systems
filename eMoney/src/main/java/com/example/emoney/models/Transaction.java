package com.example.emoney.models;

import com.example.emoney.enums.Operation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double money;

    @Column(nullable = false)
    private Operation operation;

    private String description;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name="wallet")
    private Wallet wallet;


    @PrePersist
    protected void createDateTime(){
        this.createdTime = LocalDateTime.now();
    }

}
