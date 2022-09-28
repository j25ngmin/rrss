package com.kakaopaysec.rrss.core.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler extends ThreadPoolTaskScheduler {
	@Scheduled(fixedDelay = 10000) // scheduler 끝나는 시간 기준으로 1000 간격으로 실행
    public void scheduleFixedDelayTask() throws InterruptedException {
		log.info("Scheduler");
		Thread.sleep(5000); // 5초의 작업 시간으로 가정
        System.out.println(
        "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}
