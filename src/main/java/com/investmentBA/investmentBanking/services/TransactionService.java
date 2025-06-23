package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.model.InvestmentProduct;
import com.investmentBA.investmentBanking.model.Transaction;
import com.investmentBA.investmentBanking.model.TransactionType;
import com.investmentBA.investmentBanking.model.Userr;
import com.investmentBA.investmentBanking.repository.InvestmentProductRepo;
import com.investmentBA.investmentBanking.repository.TransactionRepo;
import com.investmentBA.investmentBanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {
    private @Autowired TransactionRepo transactionRepo;
    private @Autowired UserRepository userRepository;
    private @Autowired InvestmentProductRepo productRepo;
    public Transaction buy(String username,long productId, long qty){
        Transaction transaction = new Transaction();
        Userr user = userRepository.findByUsername(username);
        Optional<InvestmentProduct> product = productRepo.findById(productId);
        transaction.setUser(user);
        transaction.setProduct(product.get());
        transaction.setType(TransactionType.BUY);
        transaction.setQuantity(qty);
        transaction.setNavAtTransaction((long) product.get().getNav());
        transaction.setTransactionTime(LocalDateTime.now());
        return transactionRepo.save(transaction);


    }
}
