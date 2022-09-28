package com.kakaopaysec.rrss.api.stock.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kakaopaysec.rrss.api.stock.entity.QStockEntity;
import com.kakaopaysec.rrss.api.stock.entity.StockEntity;
import com.kakaopaysec.rrss.core.util.CommonUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RepositoryRestResource
public class StockRepositoryImpl implements StockRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    
    @Override
	public Page<StockEntity> findAllStocks(Map<String, Object> map, Pageable pageable) {
		QStockEntity qStockEntity = new QStockEntity("stockEntity");

		QueryResults<StockEntity> result = queryFactory.selectFrom(qStockEntity).where(getBuilder(map))
				.orderBy(CommonUtil.getOrderSpecifiers(pageable.getSort(), StockEntity.class)).offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetchResults();

		return new PageImpl<>(result.getResults(), pageable, result.getTotal());

	}
    
	private BooleanBuilder getBuilder(Map<String, Object> map) {
		QStockEntity qStockEntity = new QStockEntity("stockEntity");
		BooleanBuilder builder = new BooleanBuilder();
		if (map.get("stockSeq") != null) {
			builder.and(qStockEntity.stockSeq.eq(Long.parseLong(map.get("stockSeq").toString())));
		}
		return builder;
	}



}
