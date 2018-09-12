package com.mhuffers.microservices.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mhuffers.microservices.currencyexchangeservice.model.ExchangeValue;

@Repository
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long>{
	
	@Query("Select e from Exchange_Value e where e.from = :currencyFrom and e.to = :currencyTo")
	public ExchangeValue findByFromAndTo(@Param("currencyFrom") String currencyFrom,@Param("currencyTo")String currencyTo);
	
}
