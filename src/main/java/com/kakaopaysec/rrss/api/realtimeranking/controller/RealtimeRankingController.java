package com.kakaopaysec.rrss.api.realtimeranking.controller;

import java.util.List;
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
import com.kakaopaysec.rrss.api.log.dto.RealtimeRankingDto;
import com.kakaopaysec.rrss.api.log.entity.LogEntity;
import com.kakaopaysec.rrss.api.log.service.LogService;
import com.kakaopaysec.rrss.core.scheduler.SchedulerService;
import com.sipios.springsearch.anotation.SearchSpec;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("${api.prefix.uri}/${api.version}")
public class RealtimeRankingController {

	@Autowired
	private LogService logService;

	@Value("${api.version}")
	private String version;

	@GetMapping("/realtime-ranking")
	public ResponseEntity<Object> getAllRealtimeRanking() {
		Map<String, List<RealtimeRankingDto>> LogDtos = logService.findAllRealtimeRanking();

		if (LogDtos == null || LogDtos.isEmpty()) {
			return new ResponseEntity<Object>(LogDtos, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Object>(LogDtos, HttpStatus.OK);
	}

	@GetMapping("/realtime-ranking/{subject}")
	public ResponseEntity<Object> getRealtimeRankingSubject(@PathVariable("subject") String subject,
			@PageableDefault(size = 20) Pageable pageable) {
		Page<RealtimeRankingDto> LogDtos = logService.findRealtimeRanking(subject, pageable);

		if (LogDtos == null || LogDtos.isEmpty()) {
			return new ResponseEntity<Object>(LogDtos, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Object>(LogDtos, HttpStatus.OK);
	}

}
