package com.investmentBA.investmentBanking.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDto {

    private String username;
    private BigDecimal totalInvested;
    private BigDecimal totalCurrentValue;

}
