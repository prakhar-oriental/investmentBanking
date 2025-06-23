package com.investmentBA.investmentBanking.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private InvestmentProduct product;
    private int quantity;
    private long investedAmount; // sum of all buys for this product

}
