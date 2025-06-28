package com.investmentBA.investmentBanking.services;

import com.investmentBA.investmentBanking.DTO.ItemsDto;
import com.investmentBA.investmentBanking.DTO.UserPortDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@Service
public class ExcelExporter {

    public ByteArrayInputStream exportToExcel(UserPortDto userPortDto){
        String[] headers = {"ID","Name","Type","Quantity","Nav","Invested_$","Current_Nav","Health","Health_$"};
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Sheet sheet = workbook.createSheet();
            // 🔷 Step 1: Create a bold font
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            // 🔷 Step 2: Create a cell style with the bold font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            //Create Header
            Row headerRow = sheet.createRow(0);
            for(int i = 0;i< headers.length;i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowId = 1;
            for( ItemsDto itemsDto : userPortDto.getItemsDtoList()){
                    Row row = sheet.createRow(rowId++);
                    row.createCell(0).setCellValue(itemsDto.getProductId());
                    row.createCell(1).setCellValue(itemsDto.getProductName());
                    row.createCell(2).setCellValue(itemsDto.getProductType());
                    row.createCell(3).setCellValue(itemsDto.getQuantity());
                    row.createCell(4).setCellValue(itemsDto.getNav());
                    row.createCell(5).setCellValue(itemsDto.getInvestedAmount());
                    row.createCell(6).setCellValue(itemsDto.getCurrentValue());
                    row.createCell(7).setCellValue(itemsDto.getProfitOrLoss());
                    row.createCell(8).setCellValue(itemsDto.getGainOrLoss());

            }
            workbook.write(byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
