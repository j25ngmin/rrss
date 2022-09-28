package com.kakaopaysec.rrss.api.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kakaopaysec.rrss.api.trade.entity.TradeEntity;

@RepositoryRestResource
public interface TradeRepository extends JpaRepository<TradeEntity, Long>, JpaSpecificationExecutor<TradeEntity>, TradeRepositoryCustom {

}
