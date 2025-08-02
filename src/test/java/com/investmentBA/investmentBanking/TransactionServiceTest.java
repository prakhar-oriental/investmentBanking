package com.investmentBA.investmentBanking;

import com.investmentBA.investmentBanking.DTO.TransactionDto;
import com.investmentBA.investmentBanking.model.InvestmentProduct;
import com.investmentBA.investmentBanking.model.Transaction;
import com.investmentBA.investmentBanking.model.TransactionType;
import com.investmentBA.investmentBanking.model.Userr;
import com.investmentBA.investmentBanking.repository.InvestmentProductRepo;
import com.investmentBA.investmentBanking.repository.TransactionRepo;
import com.investmentBA.investmentBanking.repository.UserRepository;
import com.investmentBA.investmentBanking.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepo transactionRepo;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InvestmentProductRepo investmentProductRepo;

    @InjectMocks
    private TransactionService transactionService;

    @Test
   public void buy_test(){
        Userr user = new Userr();
        user.setId(1L);
        user.setUsername("pop");

        InvestmentProduct investmentProduct = new InvestmentProduct();
        investmentProduct.setId(1L);
        investmentProduct.setName("kk");
        investmentProduct.setNav(100);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setProduct(investmentProduct);
        transaction.setQuantity(10);
        transaction.setNavAtTransaction(100L);

       when(userRepository.findByUsername("pop")).thenReturn(user);
       when(investmentProductRepo.findById(1L)).thenReturn(Optional.of(investmentProduct));
       when(transactionRepo.save(org.mockito.ArgumentMatchers.any(Transaction.class))).thenReturn(transaction);

     Transaction res =   transactionService.buy(user.getUsername(),investmentProduct.getId(),10 );

        assertEquals(100,res.getNavAtTransaction());
        assertEquals(10,res.getQuantity());
        assertEquals(user, res.getUser());
        assertEquals(investmentProduct, res.getProduct());
    }

    @Test
    public void gettransaction_test(){
        Userr user = new Userr();
        user.setId(1L);
        user.setUsername("pop");

        InvestmentProduct investmentProduct = new InvestmentProduct();
        investmentProduct.setId(1L);
        investmentProduct.setNav(100);
        investmentProduct.setName("kk");


        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setUser(user);
        transaction.setProduct(investmentProduct);
        transaction.setQuantity(10);
        transaction.setNavAtTransaction(100L);




        when(userRepository.findByUsername("pop")).thenReturn(user);
        when(transactionRepo.getTransactionByUserId(user.getId())).thenReturn(List.of(transaction));


        List<TransactionDto> res = transactionService.getTransaction("pop");
        TransactionDto td = res.get(0);
        System.out.println(td);
        assertEquals(1,res.size());
        assertEquals("kk",td.getProductName());


    }



    @Test
    public void test_getTransaction_whenUserNotFound_returnsNull() {
        // Arrange
        when(userRepository.findByUsername("ghost")).thenReturn(null);

        // Act
        List<TransactionDto> result = transactionService.getTransaction("ghost");

        // Assert
        assertNull(result);
    }
}
