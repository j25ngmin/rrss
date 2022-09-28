package com.kakaopaysec.rrss.api.trade.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.kakaopaysec.rrss.api.trade.dto.TradeDto;
import com.kakaopaysec.rrss.api.trade.entity.TradeEntity;
import com.kakaopaysec.rrss.api.trade.service.TradeService;
import com.kakaopaysec.rrss.core.scheduler.SchedulerService;
import com.sipios.springsearch.anotation.SearchSpec;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix.uri}/${api.version}")
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@Value("${api.version}")
	private String version;

	private final SchedulerService schedulerService;

	/**
	 * 거래 스케줄러 시작 (5초)
	 * @return
	 */
	@GetMapping("/random-trades/start")
	public ResponseEntity<Void> start() {
		schedulerService.register("test");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * 거래 스케줄러 종료
	 * @return
	 */
	@GetMapping("/random-trades/end")
	public ResponseEntity<Void> end() {
		schedulerService.remove("test");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/***
	 * 랜덤 거래 생성
	 * 
	 * @param keywords
	 * @param tradeNo
	 * @return
	 */
	@PutMapping("/random-trades")
	public ResponseEntity<Void> randomTrade() {
		tradeService.randomTrade();
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	/***
	 * 거래 목록 조회
	 * 
	 * @param keywords
	 * @param tradeNo
	 * @return
	 */
	@GetMapping("/trades")
	public ResponseEntity<Object> getTrades(@RequestParam Map<String, Object> map,
			@SearchSpec Specification<TradeEntity> specs, @PageableDefault(size = 10) Pageable pageable) {

		log.info("specs : " + specs);

		Page<TradeDto> TradeDtos = tradeService.findAllTrades(specs, pageable);

		if (TradeDtos == null || TradeDtos.isEmpty()) {
			return new ResponseEntity<Object>(TradeDtos, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Object>(TradeDtos, HttpStatus.OK);
	}

	/***
	 * 거래 단건 조회
	 * 
	 * @param keywords
	 * @param tradeNo
	 * @return
	 */
	@GetMapping("/trades/{tradeSeq}")
	public ResponseEntity<TradeDto> getTradeById(@PathVariable("tradeSeq") Long tradeSeq) {
		TradeDto tradeDto = tradeService.findById(tradeSeq);
		return new ResponseEntity<TradeDto>(tradeDto, HttpStatus.OK);
	}

	/***
	 * 거래 등록
	 * 
	 * @param keywords
	 * @param tradeNo
	 * @return
	 */
	@PostMapping("/trades")
	public ResponseEntity<Void> saveTrade(@RequestBody TradeDto tradeDto, UriComponentsBuilder ucBuilder) {

		TradeDto saveTradeDto = tradeService.saveTrade(tradeDto);
		HttpHeaders headers = new HttpHeaders();
		Long tradeSeq = saveTradeDto.getTradeSeq();

		headers.setLocation(ucBuilder.path(version + "/trades/{tradeSeq}").buildAndExpand(tradeSeq).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/***
	 * 거래 수정
	 * 
	 * @param keywords
	 * @param tradeNo
	 * @return
	 */
	@PutMapping("/trades/{tradeSeq}")
	public ResponseEntity<TradeDto> updateTrade(@PathVariable Long tradeSeq, @RequestBody TradeDto tradeDto) {
		TradeDto updateTradeDto = tradeService.updateTrade(tradeSeq, tradeDto);
		return new ResponseEntity<TradeDto>(updateTradeDto, HttpStatus.OK);
	}

	/***
	 * 거래 삭제
	 * 
	 * @param keywords
	 * @param tradeNo
	 * @return
	 */
	@DeleteMapping("/trades/{tradeSeq}")
	public ResponseEntity<Void> deleteTrades(@PathVariable Long tradeSeq) {
		tradeService.deleteTrade(tradeSeq);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
