package com.investmentBA.investmentBanking.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;
import jakarta.validation.constraints.*;

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
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "RiskLevel is required")
    private String riskLevel;
    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    private type type;
    @NotNull(message = "nav is required")
    private double nav;
    private Date createdDate;
    private Date lastUpdatedDate;
}
