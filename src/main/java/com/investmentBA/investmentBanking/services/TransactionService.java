package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.DTO.TransactionDto;
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
import java.util.ArrayList;
import java.util.List;
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
        if(user==null || !product.isPresent())
        {
            return null;
        }
        transaction.setUser(user);
        transaction.setProduct(product.get());
        transaction.setType(TransactionType.BUY);
        transaction.setQuantity(qty);
        transaction.setNavAtTransaction((long) product.get().getNav());
        transaction.setTransactionTime(LocalDateTime.now());
        return transactionRepo.save(transaction);


    }

    public Transaction sell(String username, long productId, long qty) {
        Transaction transaction = new Transaction();
        Userr user = userRepository.findByUsername(username);
        Optional<InvestmentProduct> product = productRepo.findById(productId);
        transaction.setUser(user);
        transaction.setProduct(product.get());
        transaction.setType(TransactionType.SELL);
        transaction.setQuantity(qty);
        transaction.setNavAtTransaction((long) product.get().getNav());
        transaction.setTransactionTime(LocalDateTime.now());
        return transactionRepo.save(transaction);
    }

    public List<TransactionDto> getTransaction(String username){
        Userr user = userRepository.findByUsername(username);
        if(user==null) return null;
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        List<Transaction> transactionList = transactionRepo.getTransactionByUserId(user.getId());
        for(Transaction transaction : transactionList){
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setProductName(transaction.getProduct().getName());
            transactionDto.setQuantity(transaction.getId());
            transactionDto.setType(String.valueOf(transaction.getType()));
            transactionDto.setNav(transactionDto.getNav());
            transactionDto.setTransactionTime(transaction.getTransactionTime());
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }
}
