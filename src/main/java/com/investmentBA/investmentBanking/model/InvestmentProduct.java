package com.investmentBA.investmentBanking.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String riskLevel;
    @Enumerated(EnumType.STRING)
    private type type;
    private double nav;
    private Date createdDate;
    private Date lastUpdatedDate;
}
