package com.kakaopaysec.rrss.api.trade.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.kakaopaysec.rrss.api.log.service.LogService;
import com.kakaopaysec.rrss.api.stock.dto.StockDto;
import com.kakaopaysec.rrss.api.stock.entity.StockEntity;
import com.kakaopaysec.rrss.api.stock.service.StockService;
import com.kakaopaysec.rrss.api.trade.dto.TradeDto;
import com.kakaopaysec.rrss.api.trade.entity.TradeEntity;
import com.kakaopaysec.rrss.api.trade.mapper.TradeMapStruct;
import com.kakaopaysec.rrss.api.trade.repository.TradeRepository;
import com.kakaopaysec.rrss.core.util.CommonUtil;
import com.kakaopaysec.rrss.core.util.TradeType;

@Service
public class TradeService {

	@Autowired
	private TradeRepository tradeRepository;
	private TradeMapStruct tradeMapStruct = Mappers.getMapper(TradeMapStruct.class);
	@Autowired
	private StockService stockService;
	@Autowired
	private LogService logService;

	/***
	 * 거래 목록 조회
	 * 
	 * @return
	 */
	public Page<TradeDto> findAllTrades(Specification<TradeEntity> specs, Pageable pageable) {
		Page<TradeEntity> tradeEntities = tradeRepository.findAll(specs, pageable);
		Page<TradeDto> tradeDtos = tradeEntities.map(tradeMapStruct::toDto);
		return tradeDtos;
	}

	/***
	 * 거래 목록 조회
	 * 
	 * @return
	 */
	public Page<TradeDto> findAllTrades(Map<String, Object> map, Pageable pageable) {
		Page<TradeEntity> tradeEntities = tradeRepository.findAllTrades(map, pageable);
		Page<TradeDto> tradeDtos = tradeEntities.map(tradeMapStruct::toDto);
		return tradeDtos;
	}

	/***
	 * 거래 단건 조회
	 * 
	 * @return
	 */
	public TradeDto findById(Long userSeq) {
		TradeEntity checkTradeEntity = tradeRepository.findById(userSeq)
				.orElseThrow(() -> new ResourceNotFoundException());
		TradeDto userDto = tradeMapStruct.toDto(checkTradeEntity);
		return userDto;
	}

	/***
	 * 거래 단건 조회
	 * 
	 * @return
	 */
	public TradeDto findByStockSeqAndTradeDay(Map<String, Object> map) {
		TradeEntity checkTradeEntity = tradeRepository.findByStockSeqAndTradeDay(map);
		TradeDto userDto = tradeMapStruct.toDto(checkTradeEntity);
		return userDto;
	}

	/***
	 * 거래 등록
	 * 
	 * @return
	 */
	public TradeDto saveTrade(TradeDto userDto) {
		TradeEntity tradeEntity = tradeMapStruct.toEntity(userDto);
		TradeDto tradeDtos = tradeMapStruct.toDto(tradeRepository.save(tradeEntity));
		return tradeDtos;
	}

	/***
	 * 거래 수정
	 * 
	 * @return
	 */
	public TradeDto updateTrade(Long userSeq, TradeDto userDto) {
		TradeDto updateTradeDto = findById(userSeq);
		BeanUtils.copyProperties(userDto, updateTradeDto, CommonUtil.getNullPropertyNames(userDto));
		TradeEntity tradeEntity = tradeMapStruct.toEntity(updateTradeDto);
		tradeRepository.save(tradeEntity);
		TradeDto tradeDtos = tradeMapStruct.toDto(tradeRepository.save(tradeEntity));
		return tradeDtos;
	}

	/***
	 * 랜덤 거래 내역 추가
	 * 
	 * @return
	 */
	public void randomTrade() {
		Specification<StockEntity> spec = null;
		Page<StockDto> stockDtos = stockService.findAllStocks(spec, PageRequest.of(0, Integer.MAX_VALUE));
		List<StockDto> stockDtoList = stockDtos.getContent();
		int maxStock = stockDtoList.size();
		int maxTrade = 5;
		int priceRate = 50;

		// 1. 랜덤 거래내역 추가
		int randomStockCount = (int) Math.random() * ((maxStock - 1) - (maxStock / 2)) + (maxStock / 2);
		
		// 조회수
		int randomViewCnt = (int) (Math.random() * ((10 - 0) + 1)) + 0;

		Set<Integer> randomStockIndexList = new HashSet<Integer>();
		for (int i = 0; i < randomStockCount; i++) {
			int randomStockIndex = (int) (Math.random() * (((maxStock - 1) - 0) + 1)) + 0;
			System.out.println(randomStockIndex);

			if (!randomStockIndexList.contains(randomStockIndex)) {

				StockDto stock = stockDtoList.get(randomStockIndex);
				Long saveStockSeq = stock.getStockSeq();
				
				// 거래 내역 생성 횟수
				int randomTradeCount = (int) (Math.random() * ((maxTrade - 1) + 1)) + 1;
				String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

				// 최초 내역 조회
				int savePrice = 0;
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("stockSeq", saveStockSeq);
				paramMap.put("tradeDay", today);

				TradeDto maxTradeDto = this.findByStockSeqAndTradeDay(paramMap);
				TradeDto lastTradeDto = this.findById(maxTradeDto.getTradeSeq());
				savePrice = lastTradeDto.getTradePrice();

				TradeDto saveTradeDto = new TradeDto();
				
				saveTradeDto.setStockSeq(saveStockSeq);
				saveTradeDto.setTradeDay(today);

				// 거래 내역 추가
				for (int rt = 0; rt < randomTradeCount; rt++) {
					
					// 매입/매수
					int randomTradeType = (int) (Math.random() * ((2 - 1) + 1)) + 1;
					String tradeType = TradeType.findByCodeAndTradeType(randomTradeType).getTradeType();

					int plusminusPrice = (int) savePrice / priceRate;
					int randomPrice = (int) (Math.random() * (plusminusPrice - 1) + (1)) + 1;

					int tradePrice = savePrice + ((randomTradeType == 1 ? 1 : -1) * randomPrice);
					saveTradeDto.setOldPrice(savePrice);
					saveTradeDto.setTradePrice(tradePrice);
					savePrice = tradePrice;

					LocalDateTime now = LocalDateTime.now();
					String priceTime = String.format("%02d", now.getHour()) + String.format("%02d", now.getMinute());

					saveTradeDto.setTradeType(tradeType);
					saveTradeDto.setTradeTime(priceTime);
					saveTradeDto.setCreateUserId("admin");
					saveTradeDto.setCreateDate(LocalDateTime.now());
					this.saveTrade(saveTradeDto);

					// 마지막
					if (rt + 1 == randomTradeCount) {
						paramMap.put("logDay", today);
						LogDto updateloginDto = logService.findByStockSeqAndTradeDay(paramMap);
						if (updateloginDto == null) {
							LogDto saveLogDto = new LogDto();
							saveLogDto.setStockSeq(saveStockSeq);
							saveLogDto.setEndPrice(savePrice);
							saveLogDto.setViewCnt(randomTradeCount + randomViewCnt);
							saveLogDto.setTradeCnt(randomTradeCount);
							saveLogDto.setLogDay(today);
							saveLogDto.setCreateUserId("admin");
							saveLogDto.setCreateDate(LocalDateTime.now());
							saveLogDto.setModifyUserId("admin");
							saveLogDto.setModifyDate(LocalDateTime.now());
							logService.saveLog(saveLogDto);
						} else {
							updateloginDto.setEndPrice(savePrice);
							updateloginDto.setViewCnt(updateloginDto.getViewCnt() + randomTradeCount + randomViewCnt);
							updateloginDto.setTradeCnt(updateloginDto.getTradeCnt() + randomTradeCount);
							updateloginDto.setModifyUserId("admin");
							updateloginDto.setModifyDate(LocalDateTime.now());
							logService.updateLog(updateloginDto.getLogSeq(), updateloginDto);

						}
					}
				}
			}
			randomStockIndexList.add(randomStockIndex);
		}

	}

	/***
	 * 거래 삭제
	 * 
	 * @return
	 */
	public void deleteTrade(Long userSeq) {
		TradeDto updateTradeDto = findById(userSeq);
		TradeEntity tradeEntity = tradeMapStruct.toEntity(updateTradeDto);
		tradeRepository.delete(tradeEntity);
	}

}
