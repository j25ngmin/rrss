package com.kakaopaysec.rrss.api.stock.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name="STOCK")
public class StockEntity {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="STOCK_SEQ", unique = true, nullable = false)
	private Long stockSeq;
   
    @Column (name="STOCK_NAME")
    private String stockName;
    
    @Column (name="CREATE_USER_ID")
    private String createUserId;
    
    @Column (name="CREATE_DATE")
    @CreatedDate
    private LocalDateTime createDate;

}
