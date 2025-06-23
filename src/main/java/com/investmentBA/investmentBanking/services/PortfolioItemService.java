package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.DTO.BuySell;
import com.investmentBA.investmentBanking.model.InvestmentProduct;
import com.investmentBA.investmentBanking.model.Portfolio;
import com.investmentBA.investmentBanking.model.PortfolioItem;
import com.investmentBA.investmentBanking.model.Userr;
import com.investmentBA.investmentBanking.repository.InvestmentProductRepo;
import com.investmentBA.investmentBanking.repository.PortfolioItemRepo;
import com.investmentBA.investmentBanking.repository.PortfolioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioItemService {
    @Autowired
    private  InvestmentProductRepo productRepo;
    @Autowired
    private  PortfolioRepo portfolioRepo;
    @Autowired
    private  PortfolioItemRepo itemRepo;

    private boolean flag = false;
    public  List<PortfolioItem> addPortfolioItem(Userr userr, Portfolio portfolio, BuySell buySell)
    {
        List<PortfolioItem> res = new ArrayList<>();
        res = itemRepo.findAll();
        Optional<InvestmentProduct> investmentProduct = productRepo.findById(buySell.getProductId());
        PortfolioItem portfolioItem = new PortfolioItem();
        portfolioItem.setPortfolio(portfolio);
        portfolioItem.setProduct(investmentProduct.get());
        portfolioItem.setQuantity((int) buySell.getQuantity());
        portfolioItem.setInvestedAmount((long) (buySell.getQuantity()*investmentProduct.get().getNav()));
        for(PortfolioItem portfolioItem1 : res ){
            if(portfolioItem1.getPortfolio().getUserr().getId()==portfolio.getUserr().getId() && portfolioItem1.getProduct().getId()== buySell.getProductId() ){
                portfolioItem = portfolioItem1;
                flag = true;

            }
        }
        if(flag){
            portfolioItem.setQuantity((int) (portfolioItem.getQuantity()+ buySell.getQuantity()));
            portfolioItem.setInvestedAmount((long) (portfolioItem.getInvestedAmount()+buySell.getQuantity()*investmentProduct.get().getNav()));
            itemRepo.save(portfolioItem);
            updatePortfolioValue(res,portfolio, userr);
            return res;
        }

        itemRepo.save(portfolioItem);
        res = itemRepo.findAll();
        updatePortfolioValue(res,portfolio, userr);
        return res;

    }

    private  void updatePortfolioValue(List<PortfolioItem> res, Portfolio portfolio,Userr userr) {
        long totalInvestmentAmount = 0;
        long totalCurrentValue = 0;

             for(PortfolioItem portfolioItem : res){
                 if(portfolioItem.getPortfolio().getId()== portfolio.getId())
                 {
                     totalInvestmentAmount +=   portfolioItem.getInvestedAmount();
                     Optional<InvestmentProduct> product = productRepo.findById(portfolioItem.getProduct().getId());
                     totalCurrentValue += portfolioItem.getQuantity()*product.get().getNav();
                 }
             }

         portfolio.setTotalCurrentValue(totalCurrentValue);
         portfolio.setTotalInvestmentAmount(totalInvestmentAmount);
         portfolioRepo.save(portfolio);
    }


}
