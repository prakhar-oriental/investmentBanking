package com.investmentBA.investmentBanking.controller;

import com.investmentBA.investmentBanking.DTO.UserPortDto;
import com.investmentBA.investmentBanking.model.Userr;
import com.investmentBA.investmentBanking.repository.UserRepository;
import com.investmentBA.investmentBanking.services.EmailService;
import com.investmentBA.investmentBanking.services.ExcelExporter;
import com.investmentBA.investmentBanking.services.PortfolioService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/SM")
public class MailsController {
    @Autowired private PortfolioService portfolioService;
    @Autowired private ExcelExporter excelExporter;
    @Autowired private  UserRepository userRepository;
    @Autowired private EmailService emailService;
    @GetMapping("/getMonthlyReport/{username}")
    public ResponseEntity<?> getMonthlyReport(@PathVariable String username) throws MessagingException {
       UserPortDto userPortDto = portfolioService.getPortfolio(username);
        ByteArrayInputStream exelfile = excelExporter.exportToExcel(userPortDto);
        emailService.sendMailWithExcelFile(getEmailByUsername(username),exelfile);
        return new ResponseEntity<>("Send mail", HttpStatus.OK);


    }
    public  String getEmailByUsername(String username){
        Userr existingUser = userRepository.findByUsername(username);
        if(existingUser!=null){
            return existingUser.getEmailId();
        }
        return null;
    }

}
