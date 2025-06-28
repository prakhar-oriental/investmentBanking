package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.DTO.TransactionDto;
import com.investmentBA.investmentBanking.model.Transaction;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
@Service
public class PdfGenerator {

    public  ByteArrayInputStream generateTransactionConfirmationPdf(Transaction transaction) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Transaction Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);

            // Content font
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Username: " + transaction.getUser().getUsername(), contentFont));
            document.add(new Paragraph("Stock Name: " + transaction.getProduct().getName(), contentFont));
            document.add(new Paragraph("Transaction Type: " + transaction.getType(), contentFont));
            document.add(new Paragraph("Price: " + transaction.getNavAtTransaction(), contentFont));
            document.add(new Paragraph("Quantity: " + transaction.getQuantity(), contentFont));
            document.add(new Paragraph("Time: " + transaction.getTransactionTime(), contentFont));

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Thank you for using our investment service!", contentFont));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}

