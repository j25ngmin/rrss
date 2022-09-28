package com.kakaopaysec.rrss.api.log.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RealtimeRankingDto {

    private String stockName;
    
    private int beginPrice;
        
    private int endPrice;

    private int viewCnt;
    
    private int tradeCnt;

    private int differenePrice;
    
    private double differenePriceRate;
    
}
