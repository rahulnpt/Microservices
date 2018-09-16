package com.mhuffers.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mhuffers.feign.CurrencyExchangeServiceClient;
import com.mhuffers.model.ConversionData;

@RestController
public class ConversionController {

	@Autowired
	private Environment env;
	
	@Autowired
	private CurrencyExchangeServiceClient currencyExchangeServiceClient;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//static response
	@GetMapping("currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public ConversionData calculateTotalAmount(@PathVariable("from") String from,@PathVariable("to") String to,@PathVariable("quantity") BigDecimal quantity){
		return new ConversionData(1000L, from, to, new BigDecimal(1000), new BigDecimal(65), new BigDecimal(65000), Integer.parseInt(env.getProperty("local.server.port"))); 
	}
	
	
	// using restTemplate for HTTP call
	@GetMapping("currency-converter-using-rest-template/from/{from}/to/{to}/quantity/{quantity}")
	public ConversionData calculateTotalAmountUsingRestTemplate(@PathVariable("from") String from,@PathVariable("to") String to,@PathVariable("quantity") BigDecimal quantity){
		
		Map<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<ConversionData> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", ConversionData.class,uriVariables );
		
		ConversionData response = responseEntity.getBody();
		logger.info("Logging from ConversionController {}",response);
		return new ConversionData(1000L, from, to, quantity, response.getConversionMultiple(), quantity.multiply(response.getConversionMultiple()), response.getPort()); 
	}

	// using feign for HTTP call
	@GetMapping("currency-converter-using-feign/from/{from}/to/{to}/quantity/{quantity}")
	public ConversionData calculateTotalAmountUsingFeign(@PathVariable("from") String from,@PathVariable("to") String to,@PathVariable("quantity") BigDecimal quantity){
		ConversionData response = currencyExchangeServiceClient.retriveExchangeValues(from, to);
		logger.info("Logging from ConversionController {}",response);
		return new ConversionData(response.getId(), from, to, quantity, response.getConversionMultiple(), quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
}
