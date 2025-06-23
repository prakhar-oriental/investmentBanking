package com.investmentBA.investmentBanking.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Userr user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private InvestmentProduct product;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private long quantity;
    private Long navAtTransaction;
    private LocalDateTime transactionTime;
}
