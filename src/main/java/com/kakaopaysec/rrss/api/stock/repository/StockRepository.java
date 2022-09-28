package com.kakaopaysec.rrss.api.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kakaopaysec.rrss.api.stock.entity.StockEntity;

@RepositoryRestResource
public interface StockRepository extends JpaRepository<StockEntity, Long>, JpaSpecificationExecutor<StockEntity>, StockRepositoryCustom {

}
