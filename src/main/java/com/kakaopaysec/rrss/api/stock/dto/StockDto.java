package com.kakaopaysec.rrss.api.stock.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockDto {

	private Long stockSeq;
	
    private String stockName;
    
    private String createUserId;
    
    private LocalDateTime createDate;
}
