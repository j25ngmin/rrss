package com.kakaopaysec.rrss.log;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class LogControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@DisplayName("실시간 순위 상위 5개 조회")
	@Test
	public void getRealtimeRanking() throws Exception {
		mockMvc.perform(get("/api/v1/realtime-ranking"))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	@DisplayName("카테고리별 실시간 순위 조회")
	@Test
	public void getRealtimeRankingByCategory() throws Exception {
		mockMvc.perform(get("/api/v1/realtime-ranking/VIEW?size=100"))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	@DisplayName("카테고리별 실시간 순위 조회 > 100개 이상 조회 불가")
	@Test
	public void getRealtimeRankingByCategory100More() throws Exception {
		mockMvc.perform(get("/api/v1/realtime-ranking/VIEW?size=300"))
		.andExpect(status().isBadRequest())
		.andDo(print());
	}

}
