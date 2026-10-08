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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;

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
           ExecutorService executorService =
                   Executors.newFixedThreadPool(3);

           List<Future<ItemsDto>> futures = new ArrayList<>();

           for (PortfolioItem portfolioItem : userPortfolioItemList) {

               Future<ItemsDto> future =
                       executorService.submit(
                               () -> calculatePortfolioItem(portfolioItem)
                       );

               futures.add(future);
           }
           for (Future<ItemsDto> future : futures) {
               try {

                   ItemsDto itemsDto = future.get();
                   itemsDtoList.add(itemsDto);

               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           executorService.shutdown();
           userPortDto.setItemsDtoList(itemsDtoList);
           return userPortDto;
       }

    private ItemsDto calculatePortfolioItem(
            PortfolioItem portfolioItem) {

        ItemsDto itemsDto = new ItemsDto();

        itemsDto.setNav(
                portfolioItem.getProduct().getNav()
        );

        long currentValue =
                (long) (
                        portfolioItem.getQuantity()
                                * portfolioItem.getProduct().getNav()
                );

        itemsDto.setCurrentValue(currentValue);

        itemsDto.setInvestedAmount(
                portfolioItem.getInvestedAmount()
        );

        itemsDto.setQuantity(
                portfolioItem.getQuantity()
        );

        itemsDto.setProductId(
                portfolioItem.getProduct().getId()
        );

        itemsDto.setProductName(
                portfolioItem.getProduct().getName()
        );

        itemsDto.setProductType(
                String.valueOf(
                        portfolioItem.getProduct().getType()
                )
        );

        long gainLoss =
                currentValue - portfolioItem.getInvestedAmount();

        if (gainLoss > 0) {

            itemsDto.setProfitOrLoss("Profit");

            itemsDto.setGainOrLoss(gainLoss);

        } else if (gainLoss < 0) {

            itemsDto.setProfitOrLoss("Loss");

            itemsDto.setGainOrLoss(
                    Math.abs(gainLoss)
            );

        } else {

            itemsDto.setProfitOrLoss("NA");

            itemsDto.setGainOrLoss(0);
        }
        System.out.println(
                "Started " +
                        portfolioItem.getProduct().getName() +
                        " by " +
                        Thread.currentThread().getName()
        );
        return itemsDto;
    }
}
