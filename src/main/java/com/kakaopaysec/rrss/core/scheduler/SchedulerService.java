package com.kakaopaysec.rrss.core.scheduler;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.kakaopaysec.rrss.api.trade.service.TradeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchedulerService {
     private Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @Autowired
    private TaskScheduler taskScheduler;
    
	@Autowired
	private TradeService tradeService;

    public void register(String scheduleId) {
        ScheduledFuture<?> task = taskScheduler.scheduleAtFixedRate(() ->
        		start(scheduleId) 
                ,5000);

        scheduledTasks.put(scheduleId, task);
    }

    public void remove(String scheduleId) {
    	end(scheduleId); 
        scheduledTasks.get(scheduleId).cancel(true);
    }
    
    private void start(String scheduleId) {
    	 log.info("---------- " + scheduleId + " 시작 ---------- ");
    	 tradeService.randomTrade();
    }
    
    private void end(String scheduleId) {
    	 log.info("---------- " + scheduleId + " 종료 ---------- ");
    }
}