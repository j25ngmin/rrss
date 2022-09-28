package com.kakaopaysec.rrss.api.log.controller;

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

import com.kakaopaysec.rrss.api.log.dto.LogDto;
import com.kakaopaysec.rrss.api.log.entity.LogEntity;
import com.kakaopaysec.rrss.api.log.service.LogService;
import com.sipios.springsearch.anotation.SearchSpec;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("${api.prefix.uri}/${api.version}")
public class LogController {

	@Autowired
	private LogService logService;

	@Value("${api.version}")
	private String version;

	/***
	 * 금액 목록 조회
	 * 
	 * @param keywords
	 * @param logNo
	 * @return
	 */
	@GetMapping("/logs")
	public ResponseEntity<Object> getLogs(@RequestParam Map<String, Object> map,
			@SearchSpec Specification<LogEntity> specs, @PageableDefault(size = 10) Pageable pageable) {
		Page<LogDto> LogDtos = logService.findAllLogs(specs, pageable);
		if (LogDtos == null || LogDtos.isEmpty()) {
			return new ResponseEntity<Object>(LogDtos, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Object>(LogDtos, HttpStatus.OK);
	}

	/***
	 * 금액 단건 조회
	 * 
	 * @param keywords
	 * @param logNo
	 * @return
	 */
	@GetMapping("/logs/{logSeq}")
	public ResponseEntity<LogDto> getLogById(@PathVariable("logSeq") Long logSeq) {
		LogDto logDto = logService.findById(logSeq);
		return new ResponseEntity<LogDto>(logDto, HttpStatus.OK);
	}

	/***
	 * 금액 등록
	 * 
	 * @param keywords
	 * @param logNo
	 * @return
	 */
	@PostMapping("/logs")
	public ResponseEntity<Void> saveLog(@RequestBody LogDto logDto, UriComponentsBuilder ucBuilder) {

		LogDto saveLogDto = logService.saveLog(logDto);
		HttpHeaders headers = new HttpHeaders();
		Long logSeq = saveLogDto.getLogSeq();

		headers.setLocation(ucBuilder.path(version + "/logs/{logSeq}").buildAndExpand(logSeq).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/***
	 * 금액 수정
	 * 
	 * @param keywords
	 * @param logNo
	 * @return
	 */
	@PutMapping("/logs/{logSeq}")
	public ResponseEntity<LogDto> updateLog(@PathVariable Long logSeq, @RequestBody LogDto logDto) {
		LogDto updateLogDto = logService.updateLog(logSeq, logDto);
		return new ResponseEntity<LogDto>(updateLogDto, HttpStatus.OK);
	}

	/***
	 * 금액 삭제
	 * 
	 * @param keywords
	 * @param logNo
	 * @return
	 */
	@DeleteMapping("/logs/{logSeq}")
	public ResponseEntity<Void> deleteLogs(@PathVariable Long logSeq) {
		logService.deleteLog(logSeq);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
