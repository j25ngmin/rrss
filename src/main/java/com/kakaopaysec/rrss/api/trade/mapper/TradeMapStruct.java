package com.kakaopaysec.rrss.api.trade.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.kakaopaysec.rrss.api.trade.dto.TradeDto;
import com.kakaopaysec.rrss.api.trade.entity.TradeEntity;
import com.kakaopaysec.rrss.core.util.EntityMapStruct;

@Mapper
public interface TradeMapStruct extends EntityMapStruct<TradeDto, TradeEntity> {
    TradeMapStruct INSTANCE = Mappers.getMapper(TradeMapStruct.class);
    
    @Mapping(source="stockEntity.stockSeq", target="stockSeq")
    @Mapping(source="stockEntity.stockName", target="stockName")
    TradeDto toDto(TradeEntity tradeEntity);
    
    @Mapping(source="stockSeq", target="stockEntity.stockSeq")
    TradeEntity toEntity(TradeDto tradeDto);
}
