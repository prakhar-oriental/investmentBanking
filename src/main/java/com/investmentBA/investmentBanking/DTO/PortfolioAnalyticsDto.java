package com.investmentBA.investmentBanking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioAnalyticsDto {

    private String username;

    private long totalInvested;

    private long totalCurrentValue;

    private long totalGainLoss;

    private double returnPercentage;

    private String bestInvestment;

    private String worstInvestment;

    private int investmentCount;
}