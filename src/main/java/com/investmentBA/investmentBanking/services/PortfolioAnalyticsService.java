package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.DTO.PortfolioAnalyticsDto;
import com.investmentBA.investmentBanking.model.Portfolio;
import com.investmentBA.investmentBanking.model.PortfolioItem;
import com.investmentBA.investmentBanking.model.Userr;
import com.investmentBA.investmentBanking.repository.PortfolioItemRepo;
import com.investmentBA.investmentBanking.repository.PortfolioRepo;
import com.investmentBA.investmentBanking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioAnalyticsService {

    private final UserRepository userRepository;
    private final PortfolioRepo portfolioRepo;
    private final PortfolioItemRepo portfolioItemRepo;

    public PortfolioAnalyticsService(
            UserRepository userRepository,
            PortfolioRepo portfolioRepo,
            PortfolioItemRepo portfolioItemRepo) {

        this.userRepository = userRepository;
        this.portfolioRepo = portfolioRepo;
        this.portfolioItemRepo = portfolioItemRepo;
    }

    public PortfolioAnalyticsDto generateAnalytics(String username) {

        Userr user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        Portfolio portfolio =
                portfolioRepo.findPortfolioByUserId(user.getId());

        if (portfolio == null) {
            return null;
        }

        List<PortfolioItem> items =
                portfolioItemRepo.findPortItemByPortfolioId(
                        portfolio.getId()
                );

        long totalInvested = 0;
        long totalCurrentValue = 0;

        String bestInvestment = null;
        String worstInvestment = null;

        long bestGain = Long.MIN_VALUE;
        long worstGain = Long.MAX_VALUE;

        for (PortfolioItem item : items) {

            long investedAmount =
                    item.getInvestedAmount();

            long currentValue =
                    (long) (
                        item.getQuantity()
                        * item.getProduct().getNav()
                    );

            long gain =
                    currentValue - investedAmount;

            totalInvested += investedAmount;
            totalCurrentValue += currentValue;

            if (gain > bestGain) {

                bestGain = gain;
                bestInvestment =
                        item.getProduct().getName();
            }

            if (gain < worstGain) {

                worstGain = gain;
                worstInvestment =
                        item.getProduct().getName();
            }
        }

        long totalGainLoss =
                totalCurrentValue - totalInvested;

        double returnPercentage = 0;

        if (totalInvested > 0) {

            returnPercentage =
                    ((double) totalGainLoss / totalInvested)
                    * 100;
        }

        return new PortfolioAnalyticsDto(
                username,
                totalInvested,
                totalCurrentValue,
                totalGainLoss,
                returnPercentage,
                bestInvestment,
                worstInvestment,
                items.size()
        );
    }
}