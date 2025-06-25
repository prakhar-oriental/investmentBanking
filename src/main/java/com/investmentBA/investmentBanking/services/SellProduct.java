package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.DTO.BuySell;
import com.investmentBA.investmentBanking.model.Portfolio;
import com.investmentBA.investmentBanking.model.PortfolioItem;
import com.investmentBA.investmentBanking.model.Userr;
import com.investmentBA.investmentBanking.repository.InvestmentProductRepo;
import com.investmentBA.investmentBanking.repository.PortfolioItemRepo;
import com.investmentBA.investmentBanking.repository.PortfolioRepo;
import com.investmentBA.investmentBanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellProduct {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private InvestmentProductRepo productRepo;
    @Autowired
    private PortfolioRepo portfolioRepo;
    @Autowired
    private PortfolioItemRepo portfolioItemRepo;
    @Autowired
    private PortfolioItemService portfolioItemService;
    public String sell( String username,BuySell buySell){

        boolean flag = false;
        Userr existingUser = userRepo.findByUsername(username);
        if(existingUser==null)
        {
            return "user not present";
        }
        List<Portfolio> portfoliolist  = portfolioRepo.findAll();
        Portfolio userPortfolio = null;
        for(Portfolio portfolio1 : portfoliolist){
            if(portfolio1.getUserr().getId()== existingUser.getId()){
                userPortfolio = portfolio1;
                break;
            }
        }
        List<PortfolioItem> portfolioItemList = portfolioItemRepo.findAll();
        for(PortfolioItem portfolioItem :portfolioItemList){
             if(portfolioItem.getPortfolio().getId()==userPortfolio.getId()){
                  if(portfolioItem.getProduct().getId()== buySell.getProductId() && portfolioItem.getQuantity()>= buySell.getQuantity()){
                      int quant = (int) (portfolioItem.getQuantity()- buySell.getQuantity());
                      portfolioItem.setQuantity(quant);
                      portfolioItem.setInvestedAmount((long) (quant*portfolioItem.getProduct().getNav()));
                      portfolioItemRepo.save(portfolioItem);
                      portfolioItemService.updatePortfolioValue(portfolioItemRepo.findAll(),userPortfolio,existingUser);
                      flag = true;
                  }
             }
        }
 if(flag){
     return "Sell_Success";
 }
return "Incorrect user, quantity or product id is passed by invester";
    }
}
