package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.DTO.BuySell;
import com.investmentBA.investmentBanking.model.InvestmentProduct;
import com.investmentBA.investmentBanking.model.Portfolio;
import com.investmentBA.investmentBanking.model.Userr;
import com.investmentBA.investmentBanking.repository.InvestmentProductRepo;
import com.investmentBA.investmentBanking.repository.PortfolioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {
    @Autowired private  InvestmentProductRepo productRepo;
    @Autowired private PortfolioRepo portfolioRepo;

       public  Portfolio addPortfolio(Userr userr, BuySell buySell){
           boolean hasPort = false;
           Optional<InvestmentProduct> investmentProduct = productRepo.findById(buySell.getProductId());
            Portfolio portfolio = new Portfolio();
            portfolio.setUserr(userr);

            List<Portfolio> portfolioList = portfolioRepo.findAll();
            for(Portfolio portfolio1 : portfolioList){
                if(portfolio1.getUserr().getId()==userr.getId())
                {
                    portfolio= portfolio1;
                    hasPort = true;
                }
            }
            if(!hasPort) {
              return   portfolioRepo.save(portfolio);
            }
            return portfolio;
       }
}
