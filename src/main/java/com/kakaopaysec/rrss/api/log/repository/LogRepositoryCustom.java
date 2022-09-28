package com.kakaopaysec.rrss.api.log.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kakaopaysec.rrss.api.log.dto.RealtimeRankingDto;
import com.kakaopaysec.rrss.api.log.entity.LogEntity;

public interface LogRepositoryCustom {
	Page<LogEntity> findAllLogs(Map<String, Object> map, Pageable pageable);
	LogEntity findByStockSeqAndLogDay(Map<String, Object> map);
	Page<RealtimeRankingDto> findRealtimeRanking(Map<String, Object> map, Pageable pageable);
}
