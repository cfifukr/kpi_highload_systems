package com.example.emoney.models;


import com.example.emoney.enums.Currency;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;


@Entity
@Data
@NoArgsConstructor
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String goal;

    @Column(nullable = false)
    private Double moneyNeed;

    private Double moneyHave;

    @Column(nullable = false)
    private Boolean isAccomplished;

    @Column(nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name="user")
    private User user;

    public Goal(String goal, Double moneyNeed, Currency currency) {
        this.goal = goal;
        this.moneyNeed = moneyNeed;
        this.currency = currency;
        this.moneyHave = 0.00;
        this.isAccomplished = false;
    }


}
