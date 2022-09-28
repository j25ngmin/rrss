package com.kakaopaysec.rrss.api.trade.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kakaopaysec.rrss.api.trade.entity.QTradeEntity;
import com.kakaopaysec.rrss.api.trade.entity.TradeEntity;
import com.kakaopaysec.rrss.core.util.CommonUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RepositoryRestResource
public class TradeRepositoryImpl implements TradeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    
    
    @Override
	public Page<TradeEntity> findAllTrades(Map<String, Object> map, Pageable pageable) {
		QTradeEntity qTradeEntity = new QTradeEntity("tradeEntity");

		QueryResults<TradeEntity> result = queryFactory.selectFrom(qTradeEntity).where(getBuilder(map))
				.orderBy(CommonUtil.getOrderSpecifiers(pageable.getSort(), TradeEntity.class)).offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetchResults();

		return new PageImpl<>(result.getResults(), pageable, result.getTotal());

	}
    
    @Override
	public TradeEntity findByStockSeqAndTradeDay(Map<String, Object> map) {
		QTradeEntity qTrade = new QTradeEntity("tradeEntity");

		TradeEntity result = 
			queryFactory
			.select(
					Projections.fields(TradeEntity.class
			, qTrade.stockEntity.stockSeq
			, qTrade.tradeDay
			, qTrade.tradeSeq.max().as("tradeSeq")))
			.from(qTrade)
			.where(getBuilder(map))
			.groupBy(qTrade.stockEntity.stockSeq, qTrade.tradeDay)
		.fetchFirst();
		
		return result;

	}
    
    
	private BooleanBuilder getBuilder(Map<String, Object> map) {
		QTradeEntity qTrade = new QTradeEntity("tradeEntity");
		BooleanBuilder builder = new BooleanBuilder();
		if (map.get("stockSeq") != null) {
			builder.and(qTrade.stockEntity.stockSeq.eq(Long.parseLong(map.get("stockSeq").toString())));
		}
		if (map.get("tradeDay") != null) {
			builder.and(qTrade.tradeDay.eq(map.get("tradeDay").toString()));
		}
		return builder;
	}



}
