package com.kakaopaysec.rrss.api.stock.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.kakaopaysec.rrss.api.stock.dto.StockDto;
import com.kakaopaysec.rrss.api.stock.entity.StockEntity;
import com.kakaopaysec.rrss.core.util.EntityMapStruct;

@Mapper
public interface StockMapStruct extends EntityMapStruct<StockDto, StockEntity> {
    StockMapStruct INSTANCE = Mappers.getMapper(StockMapStruct.class);
}
