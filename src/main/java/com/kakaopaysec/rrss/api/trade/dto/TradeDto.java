package com.kakaopaysec.rrss.api.trade.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TradeDto {

	private Long tradeSeq;
   
    private Long stockSeq;
    
    private String stockName;
    
    private String tradeType;
    
    private int oldPrice;
    
    private int tradePrice;

    private String tradeDay;
    
    private String tradeTime;
    
    private String createUserId;
    
    private LocalDateTime createDate;

}
