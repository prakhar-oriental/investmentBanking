package com.investmentBA.investmentBanking.controller;

import com.investmentBA.investmentBanking.DTO.BuySell;
import com.investmentBA.investmentBanking.DTO.TransactionDto;
import com.investmentBA.investmentBanking.DTO.UserPortDto;
import com.investmentBA.investmentBanking.model.Portfolio;
import com.investmentBA.investmentBanking.model.Transaction;
import com.investmentBA.investmentBanking.services.PortfolioItemService;
import com.investmentBA.investmentBanking.services.PortfolioService;
import com.investmentBA.investmentBanking.services.SellProduct;
import com.investmentBA.investmentBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private PortfolioItemService portfolioItemService;
    @Autowired
    private SellProduct sellProduct;

    @PostMapping("/buyProduct/{username}")
    public ResponseEntity<?> buyProduct(@PathVariable String username, @RequestBody BuySell buySell){
      Transaction transaction =  transactionService.buy(username,buySell.getProductId(), buySell.getQuantity());
      if(transaction!=null) {
          Portfolio portfolio = portfolioService.addPortfolio(transaction.getUser(), buySell);
          portfolioItemService.addPortfolioItem(transaction.getUser(), portfolio, buySell);
          return new ResponseEntity<>("Buy Success", HttpStatus.OK);
      }else {
          return new ResponseEntity<>("Buy Falied", HttpStatus.OK);
      }
    }

    @GetMapping("/sellProduct/{username}")
    public ResponseEntity<?> sellProduct(@PathVariable String username, @RequestBody BuySell buySell){
       String result =   sellProduct.sell(username, buySell);
        if(result.equals("Sell_Success")){
            Transaction transaction =  transactionService.sell(username,buySell.getProductId(), buySell.getQuantity());
            return new ResponseEntity<>("Sell Success", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Sell failed", HttpStatus.CONFLICT);
        }

    }

    @PostMapping("/getUserPortfolio/{username}")
    public ResponseEntity<?> getUserPortfolio(@PathVariable String username){
      UserPortDto userPortDto =  portfolioService.getPortfolio(username);
        return new ResponseEntity<>(userPortDto, HttpStatus.OK);
    }

    @GetMapping("getUserTransaction/{username}")
    public ResponseEntity<?> getUserTransaction(@PathVariable String username){

        List<TransactionDto> transactionDtoList = transactionService.getTransaction(username);
        if(transactionDtoList!=null){
            return new ResponseEntity<>(transactionDtoList, HttpStatus.OK);
        }else return new ResponseEntity<>("invalid user", HttpStatus.CONFLICT);
    }

}
