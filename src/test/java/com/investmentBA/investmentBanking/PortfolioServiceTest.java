package com.investmentBA.investmentBanking;

import com.investmentBA.investmentBanking.DTO.BuySell;
import com.investmentBA.investmentBanking.DTO.ItemsDto;
import com.investmentBA.investmentBanking.DTO.UserPortDto;
import com.investmentBA.investmentBanking.model.*;
import com.investmentBA.investmentBanking.repository.InvestmentProductRepo;
import com.investmentBA.investmentBanking.repository.PortfolioItemRepo;
import com.investmentBA.investmentBanking.repository.PortfolioRepo;
import com.investmentBA.investmentBanking.repository.UserRepository;
import com.investmentBA.investmentBanking.services.PortfolioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock private InvestmentProductRepo productRepo;
    @Mock private PortfolioRepo portfolioRepo;
    @Mock private PortfolioItemRepo portfolioItemRepo;
    @Mock private UserRepository userRepository;
    @InjectMocks private PortfolioService portfolioService;

    private Userr user;
    private InvestmentProduct investmentProduct;
    private Portfolio portfolio;
    private PortfolioItem  portfolioItem;

    @BeforeEach
    public void setup() {
        user = new Userr();
        user.setId(1L);
        user.setUsername("pop");

        investmentProduct = new InvestmentProduct();
        investmentProduct.setId(1L);
        investmentProduct.setNav(100);
        investmentProduct.setName("kk");

        portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setUserr(user);
        portfolio.setTotalCurrentValue(200);
        portfolio.setTotalInvestmentAmount(300);

        portfolioItem = new PortfolioItem();
        portfolioItem.setPortfolio(portfolio);
        portfolioItem.setProduct(investmentProduct);
        portfolioItem.setQuantity(5);
        portfolioItem.setInvestedAmount(300);
    }

    @Test
    void addPortfolio() {
        BuySell buySell = new BuySell(1L,5);
        when(productRepo.findById(1L)).thenReturn(Optional.of(investmentProduct));
        when(portfolioRepo.findAll()).thenReturn(List.of(portfolio));
        Portfolio res = portfolioService.addPortfolio(user,buySell);
        assertEquals(200,res.getTotalCurrentValue());
        assertEquals(300,res.getTotalInvestmentAmount());
        assertEquals(1L,user.getId());
    }

    @Test
    void getPortfolio() {

        when(userRepository.findByUsername("pop")).thenReturn(user);
        when(portfolioRepo.findPortfolioByUserId(1L)).thenReturn(portfolio);
        when(portfolioItemRepo.findPortItemByPortfolioId(1L)).thenReturn(List.of(portfolioItem));

        UserPortDto userPortDto = portfolioService.getPortfolio("pop");
        List<ItemsDto> list =  userPortDto.getItemsDtoList();
        assertEquals("pop",userPortDto.getUsername());
        assertEquals(100,list.get(0).getNav());
    }
}