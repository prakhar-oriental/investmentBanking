package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.DTO.BuySell;
import com.investmentBA.investmentBanking.DTO.ItemsDto;
import com.investmentBA.investmentBanking.DTO.UserPortDto;
import com.investmentBA.investmentBanking.model.InvestmentProduct;
import com.investmentBA.investmentBanking.model.Portfolio;
import com.investmentBA.investmentBanking.model.PortfolioItem;
import com.investmentBA.investmentBanking.model.Userr;
import com.investmentBA.investmentBanking.repository.InvestmentProductRepo;
import com.investmentBA.investmentBanking.repository.PortfolioItemRepo;
import com.investmentBA.investmentBanking.repository.PortfolioRepo;
import com.investmentBA.investmentBanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {
    @Autowired private  InvestmentProductRepo productRepo;
    @Autowired private PortfolioRepo portfolioRepo;
    @Autowired private PortfolioItemRepo portfolioItemRepo;
    @Autowired private UserRepository userRepository;
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
       public UserPortDto getPortfolio(String username){
           Userr existing = userRepository.findByUsername(username);
           Portfolio userPortfolio = portfolioRepo.findPortfolioByUserId(existing.getId());
           List<PortfolioItem> userPortfolioItemList = portfolioItemRepo.findPortItemByPortfolioId(userPortfolio.getId());
           List<ItemsDto> itemsDtoList = new ArrayList<>();
           if(existing==null) return null;
           UserPortDto userPortDto = new UserPortDto();
           userPortDto.setUsername(username);
           userPortDto.setTotalCurrentValue(userPortfolio.getTotalCurrentValue());
           userPortDto.setTotalInvestedAmount(userPortfolio.getTotalInvestmentAmount());
           userPortDto.setGainOrLoss(userPortfolio.getTotalCurrentValue()-userPortfolio.getTotalInvestmentAmount());
           for(PortfolioItem portfolioItem : userPortfolioItemList)
           {
               ItemsDto itemsDto = new ItemsDto();
               itemsDto.setNav(portfolioItem.getProduct().getNav());
               itemsDto.setCurrentValue((long) (portfolioItem.getQuantity()*portfolioItem.getProduct().getNav()));
               itemsDto.setInvestedAmount(portfolioItem.getInvestedAmount());
               itemsDto.setQuantity(portfolioItem.getQuantity());
               itemsDto.setProductId(portfolioItem.getPortfolio().getId());
               itemsDto.setProductName(portfolioItem.getProduct().getName());
               itemsDto.setProductType(String.valueOf(portfolioItem.getProduct().getType()));
               if(portfolioItem.getQuantity()*portfolioItem.getProduct().getNav()>portfolioItem.getInvestedAmount()){
                   itemsDto.setProfitOrLoss("Profit");
                   itemsDto.setGainOrLoss((long) (portfolioItem.getQuantity()*portfolioItem.getProduct().getNav()-portfolioItem.getInvestedAmount()));
               }else{
                   itemsDto.setProfitOrLoss("Loss");
                   itemsDto.setGainOrLoss((long) (Math.abs(portfolioItem.getQuantity()*portfolioItem.getProduct().getNav()-portfolioItem.getInvestedAmount())));
               }
               itemsDtoList.add(itemsDto);
           }
           userPortDto.setItemsDtoList(itemsDtoList);
           return userPortDto;
       }
}
