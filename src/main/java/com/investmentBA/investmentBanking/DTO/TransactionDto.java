package com.investmentBA.investmentBanking.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String productName;
    private String type;
    private long quantity;
    private long nav;
    private LocalDateTime transactionTime;
}
