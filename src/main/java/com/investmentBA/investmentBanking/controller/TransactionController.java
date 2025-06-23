package com.investmentBA.investmentBanking.controller;

import com.investmentBA.investmentBanking.DTO.BuySell;
import com.investmentBA.investmentBanking.model.Portfolio;
import com.investmentBA.investmentBanking.model.Transaction;
import com.investmentBA.investmentBanking.services.PortfolioItemService;
import com.investmentBA.investmentBanking.services.PortfolioService;
import com.investmentBA.investmentBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private PortfolioItemService portfolioItemService;

    @PostMapping("/buyProduct/{username}")
    public ResponseEntity<?> buyProduct(@PathVariable String username, @RequestBody BuySell buySell){
      Transaction transaction =  transactionService.buy(username,buySell.getProductId(), buySell.getQuantity());
       Portfolio portfolio =  portfolioService.addPortfolio(transaction.getUser(),buySell);
        portfolioItemService.addPortfolioItem(transaction.getUser(),portfolio,buySell);
        return new ResponseEntity<>("Buy Success", HttpStatus.OK);
    }


}
