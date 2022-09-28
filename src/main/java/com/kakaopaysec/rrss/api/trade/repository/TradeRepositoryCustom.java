package com.kakaopaysec.rrss.api.trade.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kakaopaysec.rrss.api.trade.entity.TradeEntity;

public interface TradeRepositoryCustom {
	Page<TradeEntity> findAllTrades(Map<String, Object> map, Pageable pageable);
	TradeEntity findByStockSeqAndTradeDay(Map<String, Object> map);
}
