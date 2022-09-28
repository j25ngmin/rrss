package com.kakaopaysec.rrss.api.log.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.kakaopaysec.rrss.api.log.dto.LogDto;
import com.kakaopaysec.rrss.api.log.dto.RealtimeRankingDto;
import com.kakaopaysec.rrss.api.log.entity.LogEntity;
import com.kakaopaysec.rrss.api.log.mapper.LogMapStruct;
import com.kakaopaysec.rrss.api.log.repository.LogRepository;
import com.kakaopaysec.rrss.core.error.ValidationCustomException;
import com.kakaopaysec.rrss.core.util.CommonUtil;
import com.kakaopaysec.rrss.core.util.Subject;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;
    private LogMapStruct logMapStruct = Mappers.getMapper(LogMapStruct.class);

    /***
     * 로그 목록 조회
     * @return
     */
    @SuppressWarnings("null")
	public Map<String, List<RealtimeRankingDto>> findAllRealtimeRanking() {
    	Map<String, List<RealtimeRankingDto>> subjectMap = new HashMap<String, List<RealtimeRankingDto>>();
    	Map<String, Object> map = new HashMap<>();
		String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		map.put("logDay", today);
    	// 많이 본
    	map.put("subject", Subject.INCREASE.getKey());
    	subjectMap.put(Subject.INCREASE.getKey(), logRepository.findRealtimeRanking(map, PageRequest.of(0, 5)).getContent());
    	// 많이 오른
    	map.put("subject", Subject.REDUCE.getKey());
    	subjectMap.put(Subject.REDUCE.getKey(), logRepository.findRealtimeRanking(map, PageRequest.of(0, 5)).getContent());
    	// 많이 내린
    	map.put("subject", Subject.VIEW.getKey());
    	subjectMap.put(Subject.VIEW.getKey(), logRepository.findRealtimeRanking(map, PageRequest.of(0, 5)).getContent());
    	// 거래량 많은 
    	map.put("subject", Subject.VOLUME.getKey());
    	subjectMap.put(Subject.VOLUME.getKey(), logRepository.findRealtimeRanking(map, PageRequest.of(0, 5)).getContent());
        return subjectMap;
    }
    
    /***
     * 로그 목록 조회
     * @return
     */
    public Page<RealtimeRankingDto> findRealtimeRanking(String subject, Pageable pageable) {
    	Map<String, Object> map = new HashMap<>();
		String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		map.put("logDay", today);
    	map.put("subject", subject);
    	if(pageable.getPageSize() > 100) {
    		throw new ValidationCustomException("최대 100개까지만 데이터를 제공합니다.");
    	}
    	Page<RealtimeRankingDto> logDtos = logRepository.findRealtimeRanking(map, pageable);
        return logDtos;
    }
    
    /***
     * 로그 목록 조회
     * @return
     */
    public Page<LogDto> findAllLogs(Specification<LogEntity> specs, Pageable pageable) {
    	Page<LogEntity> logEntities = logRepository.findAll(specs, pageable);
    	Page<LogDto> logDtos = logEntities.map(logMapStruct::toDto);
        return logDtos;
    }
    
    /***
     * 로그 목록 조회
     * @return
     */
    public Page<LogDto> findAllLogs(Map<String, Object> map, Pageable pageable) {
    	Page<LogEntity> logEntities = logRepository.findAllLogs(map, pageable);
    	Page<LogDto> logDtos = logEntities.map(logMapStruct::toDto);
        return logDtos;
    }
        
    /***
     * 로그 단건 조회
     * @return
     */
    public LogDto findById(Long userSeq) {
    	LogEntity checkLogEntity = logRepository.findById(userSeq).orElseThrow(() -> new ResourceNotFoundException());
        LogDto userDto = logMapStruct.toDto(checkLogEntity);
        return userDto;
    }
    
    /***
     * 로그 단건 조회 (종목코드, 금액)
     * @return
     */
    public LogDto findByStockSeqAndTradeDay(Map<String, Object> map) {
    	//LogEntity checkLogEntity = logRepository.findByStockSeqAndLogDay(Long.parseLong(map.get("stockSeq").toString()), map.get("logDay").toString());
    	LogEntity checkLogEntity = logRepository.findByStockSeqAndLogDay(map);
        LogDto userDto = logMapStruct.toDto(checkLogEntity);
        return userDto;
    }
    
    /***
     * 로그 등록
     * @return
     */
    public LogDto saveLog(LogDto userDto) {
    	LogEntity logEntity = logMapStruct.toEntity(userDto);
        LogDto logDtos = logMapStruct.toDto(logRepository.save(logEntity));
        return logDtos;
    }
    
    /***
     * 로그 수정
     * @return
     */
    public LogDto updateLog(Long logSeq, LogDto logDto) {
    	LogDto updateLogDto = findById(logSeq);
    	BeanUtils.copyProperties(logDto, updateLogDto, CommonUtil.getNullPropertyNames(logDto));
    	LogEntity logEntity = logMapStruct.toEntity(updateLogDto);
    	logRepository.save(logEntity);
        LogDto logDtos = logMapStruct.toDto(logRepository.save(logEntity));
        return logDtos;
    }
    
    /***
     * 로그 삭제
     * @return
     */
    public void deleteLog(Long userSeq) {
    	LogDto updateLogDto = findById(userSeq);
    	LogEntity logEntity = logMapStruct.toEntity(updateLogDto);
    	logRepository.delete(logEntity);
    }

}
