package com.kakaopaysec.rrss.api.trade.entity;

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
@Table(name="TRADE")
public class TradeEntity {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="TRADE_SEQ", unique = true, nullable = false)
	private Long tradeSeq;
   
	@ManyToOne
	@JoinColumn(name="STOCK_SEQ")
	//@Column (name="STOCK_SEQ")
	//private Long stockSeq;
	private StockEntity stockEntity;
	
	@Column (name="TRADE_TYPE")
    private String tradeType;
    
	@Column (name="OLD_PRICE")
    private int oldPrice;
    
    @Column (name="TRADE_PRICE")
    private int tradePrice;

    @Column (name="TRADE_DAY")
    private String tradeDay;
    
    @Column (name="TRADE_TIME")
    private String tradeTime;
    
    @Column (name="CREATE_USER_ID")
    private String createUserId;
    
    @Column (name="CREATE_DATE")
    @CreatedDate
    private LocalDateTime createDate;

}
