package com.investmentBA.investmentBanking.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

     public void sendMailWithExcelFile(String toEmail,ByteArrayInputStream in) throws MessagingException {
         MimeMessage mimeMessage = javaMailSender.createMimeMessage();
         MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
         mimeMessageHelper.setTo(toEmail);
         mimeMessageHelper.setSubject("User Monthly Portfolio Report");
         mimeMessageHelper.setText("Hi! Please find attached your Monthly Portfolio Report.");

         mimeMessageHelper.addAttachment("MonthlyPortReport.xlsx", new ByteArrayResource(in.readAllBytes()));
         javaMailSender.send(mimeMessage);


     }
    public void sendMailWithPdf(String toEmail,ByteArrayInputStream in) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Transaction Confirmation Invoice");
        mimeMessageHelper.setText("Hi! Please find attached your Invoice.");

        mimeMessageHelper.addAttachment("Invoice.pdf", new ByteArrayResource(in.readAllBytes()));
        javaMailSender.send(mimeMessage);


    }
}
