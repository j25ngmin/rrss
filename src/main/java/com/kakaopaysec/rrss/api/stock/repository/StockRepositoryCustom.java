package com.kakaopaysec.rrss.api.stock.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kakaopaysec.rrss.api.stock.entity.StockEntity;

public interface StockRepositoryCustom {
	Page<StockEntity> findAllStocks(Map<String, Object> map, Pageable pageable);
}
