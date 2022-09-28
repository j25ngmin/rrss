package com.kakaopaysec.rrss.api.log.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kakaopaysec.rrss.api.stock.entity.StockEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="LOG")
public class LogEntity {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="LOG_SEQ", unique = true, nullable = false)
	private Long logSeq;
   	
	@ManyToOne
	@JoinColumn(name="STOCK_SEQ")
	//@Column (name="STOCK_SEQ")
	//private Long stockSeq;
	private StockEntity stockEntity;
    
    @Column (name="BEGIN_PRICE")
    private int beginPrice;
        
    @Column (name="END_PRICE")
    private int endPrice;

    @Column (name="VIEW_CNT")
    private int viewCnt;
    
    @Column (name="TRADE_CNT")
    private int tradeCnt;
    
    @Column (name="LOG_DAY")
    private String logDay;
    
    @Column (name="CREATE_USER_ID")
    private String createUserId;
    
    @Column (name="CREATE_DATE")
    @CreatedDate
    private LocalDateTime createDate;
    
    @Column (name="MODIFY_USER_ID")
    private String modifyUserId;
    
    @Column (name="MODIFY_DATE")
    @CreatedDate
    private LocalDateTime modifyDate;
    
}
