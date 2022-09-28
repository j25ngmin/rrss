package com.kakaopaysec.rrss.api.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kakaopaysec.rrss.api.log.entity.LogEntity;

@RepositoryRestResource
public interface LogRepository extends JpaRepository<LogEntity, Long>, JpaSpecificationExecutor<LogEntity>, LogRepositoryCustom {
	//LogEntity findByStockSeqAndLogDay (Long stockSeq, String logDay);
}
