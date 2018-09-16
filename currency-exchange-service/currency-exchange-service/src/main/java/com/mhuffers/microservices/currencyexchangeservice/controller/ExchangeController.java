package com.mhuffers.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mhuffers.microservices.currencyexchangeservice.model.ExchangeValue;
import com.mhuffers.microservices.currencyexchangeservice.repository.ExchangeValueRepository;

@RestController
public class ExchangeController {

	@Autowired
	private Environment env;
	
	@Autowired
	private ExchangeValueRepository exchangeValueRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//hardcoded response
	/*@GetMapping("currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retriveExchangeValues(@PathVariable String from,@PathVariable String to){
		ExchangeValue exchangeValue = new ExchangeValue(1000L,from, to, new BigDecimal(65));
		exchangeValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return exchangeValue;
	}*/
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retriveExchangeValues(@PathVariable String from,@PathVariable String to){
		ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);
		exchangeValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		
		logger.info("Logging from ExchangeController {}",exchangeValue);
		
		return exchangeValue;
	}
}
