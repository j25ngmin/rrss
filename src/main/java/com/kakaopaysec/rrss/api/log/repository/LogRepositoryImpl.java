package com.kakaopaysec.rrss.api.log.repository;

import java.math.RoundingMode;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kakaopaysec.rrss.api.log.dto.RealtimeRankingDto;
import com.kakaopaysec.rrss.api.log.entity.LogEntity;
import com.kakaopaysec.rrss.api.log.entity.QLogEntity;
import com.kakaopaysec.rrss.core.util.CommonUtil;
import com.kakaopaysec.rrss.core.util.Subject;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RepositoryRestResource
public class LogRepositoryImpl implements LogRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<LogEntity> findAllLogs(Map<String, Object> map, Pageable pageable) {
		QLogEntity qLog = new QLogEntity("logEntity");

		QueryResults<LogEntity> result = queryFactory.selectFrom(qLog).where(getBuilder(map))
				.orderBy(CommonUtil.getOrderSpecifiers(pageable.getSort(), LogEntity.class))
				.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

		return new PageImpl<>(result.getResults(), pageable, result.getTotal());

	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<RealtimeRankingDto> findRealtimeRanking(Map<String, Object> map, Pageable pageable) {
		QLogEntity qLog = new QLogEntity("logEntity");
		OrderSpecifier<?> os = (qLog.logSeq).desc();

		if (map.get("subject") != null) {
			if(Subject.INCREASE.getKey().equals(map.get("subject").toString())) {
				Path<Integer> sortDifferenePrice = Expressions.datePath(Integer.class, "differenePriceRate");
				os = ((ComparableExpressionBase<Integer>) sortDifferenePrice).desc();
			}
			else if(Subject.REDUCE.getKey().equals(map.get("subject").toString())) {
				Path<Integer> sortDifferenePrice = Expressions.datePath(Integer.class, "differenePriceRate");
				os = ((ComparableExpressionBase<Integer>) sortDifferenePrice).asc();
			}
			else if(Subject.VIEW.getKey().equals(map.get("subject").toString())) {
				os = (qLog.viewCnt).desc();
			}
			else if(Subject.VOLUME.getKey().equals(map.get("subject").toString())) {
				os = (qLog.tradeCnt).desc();
			}
		}
		
		QueryResults<RealtimeRankingDto> result = queryFactory
				.select(
					 Projections.fields(RealtimeRankingDto.class
					,qLog.stockEntity.stockSeq
					,qLog.stockEntity.stockName
					,qLog.beginPrice
					,qLog.endPrice
					,qLog.viewCnt
					,qLog.tradeCnt
					,qLog.endPrice.subtract(qLog.beginPrice).as("differenePrice")
		        	, new CaseBuilder()
		      		  .when(qLog.endPrice.ne(qLog.beginPrice))
		      		  .then((qLog.endPrice.subtract(qLog.beginPrice).multiply(10000).divide(qLog.beginPrice).doubleValue().divide(100.0))) //.divide(qLog.beginPrice).divide(100.0))
		      		  .otherwise(0.0).as("differenePriceRate")
					))
				.from(qLog)
				.where(getBuilder(map))
				.orderBy(os)
				.offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetchResults();
		
		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}
	
	@Override
	public LogEntity findByStockSeqAndLogDay(Map<String, Object> map) {
		QLogEntity qLog = new QLogEntity("logEntity");
		LogEntity result = queryFactory.selectFrom(qLog).where(getBuilder(map)).fetchFirst();
		return result;
	}

	private BooleanBuilder getBuilder(Map<String, Object> map) {
		QLogEntity qLog = new QLogEntity("logEntity");
		BooleanBuilder builder = new BooleanBuilder();
		if (map.get("stockSeq") != null) {
			builder.and(qLog.stockEntity.stockSeq.eq(Long.parseLong(map.get("stockSeq").toString())));
		}
		if (map.get("logDay") != null) {
			builder.and(qLog.logDay.eq(map.get("logDay").toString()));
		}
		return builder;
	}

}
