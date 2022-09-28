package com.kakaopaysec.rrss.api.trade.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kakaopaysec.rrss.api.trade.dto.TradeDto;

@Mapper
@Repository
public interface TradeMapper {

	List<TradeDto> findByXML(Map<String, Object> map);
}
