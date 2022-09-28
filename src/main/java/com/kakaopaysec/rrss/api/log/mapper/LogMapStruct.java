package com.kakaopaysec.rrss.api.log.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.kakaopaysec.rrss.api.log.dto.LogDto;
import com.kakaopaysec.rrss.api.log.entity.LogEntity;
import com.kakaopaysec.rrss.core.util.EntityMapStruct;

@Mapper
public interface LogMapStruct extends EntityMapStruct<LogDto, LogEntity> {
    LogMapStruct INSTANCE = Mappers.getMapper(LogMapStruct.class);
    
    @Mapping(source="stockEntity.stockSeq", target="stockSeq")
    LogDto toDto(LogEntity logEntity);
    
    @Mapping(source="stockSeq", target="stockEntity.stockSeq")
    LogEntity toEntity(LogDto logDto);
}
