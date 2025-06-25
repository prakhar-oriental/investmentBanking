package com.investmentBA.investmentBanking.DTO;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDto {
         private long     productId;
         private String   productName;
         private String   productType;
         private int      quantity;
         private double   nav;
         private long     investedAmount;
         private long     currentValue;
         private String   ProfitOrLoss;
         private long     gainOrLoss;

}
