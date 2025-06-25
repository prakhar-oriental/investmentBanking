package com.investmentBA.investmentBanking.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPortDto {
        private String username;
        private long   totalInvestedAmount;
        private long   totalCurrentValue;
        private long   gainOrLoss;
        private List<ItemsDto> itemsDtoList;
}
