package com.kakaopaysec.rrss.api.stock.service;

import java.util.Map;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.kakaopaysec.rrss.api.stock.dto.StockDto;
import com.kakaopaysec.rrss.api.stock.entity.StockEntity;
import com.kakaopaysec.rrss.api.stock.mapper.StockMapStruct;
import com.kakaopaysec.rrss.api.stock.repository.StockRepository;
import com.kakaopaysec.rrss.core.util.CommonUtil;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    private StockMapStruct stockMapStruct = Mappers.getMapper(StockMapStruct.class);

    /***
     * 종목 목록 조회
     * @return
     */
    public Page<StockDto> findAllStocks(Specification<StockEntity> specs, Pageable pageable) {
    	Page<StockEntity> stockEntities = stockRepository.findAll(specs, pageable);
    	Page<StockDto> stockDtos = stockEntities.map(stockMapStruct::toDto);
        return stockDtos;
    }
    
    /***
     * 종목 목록 조회
     * @return
     */
    public Page<StockDto> findAllStocks(Map<String, Object> map, Pageable pageable) {
    	Page<StockEntity> stockEntities = stockRepository.findAllStocks(map, pageable);
    	Page<StockDto> stockDtos = stockEntities.map(stockMapStruct::toDto);
        return stockDtos;
    }
        
    /***
     * 종목 단건 조회
     * @return
     */
    public StockDto findById(Long userSeq) {
    	StockEntity checkStockEntity = stockRepository.findById(userSeq).orElseThrow(() -> new ResourceNotFoundException());
        StockDto userDto = stockMapStruct.toDto(checkStockEntity);
        return userDto;
    }
    
    /***
     * 종목 등록
     * @return
     */
    public StockDto saveStock(StockDto userDto) {
    	StockEntity stockEntity = stockMapStruct.toEntity(userDto);
        StockDto stockDtos = stockMapStruct.toDto(stockRepository.save(stockEntity));
        return stockDtos;
    }
    
    /***
     * 종목 수정
     * @return
     */
    public StockDto updateStock(Long userSeq, StockDto userDto) {
    	StockDto updateStockDto = findById(userSeq);
    	BeanUtils.copyProperties(userDto, updateStockDto, CommonUtil.getNullPropertyNames(userDto));
    	StockEntity stockEntity = stockMapStruct.toEntity(updateStockDto);
    	stockRepository.save(stockEntity);
        StockDto stockDtos = stockMapStruct.toDto(stockRepository.save(stockEntity));
        return stockDtos;
    }
    
    /***
     * 종목 삭제
     * @return
     */
    public void deleteStock(Long userSeq) {
    	StockDto updateStockDto = findById(userSeq);
    	StockEntity stockEntity = stockMapStruct.toEntity(updateStockDto);
    	stockRepository.delete(stockEntity);
    }

}
