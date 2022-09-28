package com.kakaopaysec.rrss.api.log.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LogDto {

	private Long logSeq;
   
    private Long stockSeq;
    
    private int beginPrice;
        
    private int endPrice;

    private int viewCnt;
    
    private int tradeCnt;

    private String logDay;
    
    private String createUserId;
    
    private LocalDateTime createDate;
    
    private String modifyUserId;
    
    private LocalDateTime modifyDate;

}
