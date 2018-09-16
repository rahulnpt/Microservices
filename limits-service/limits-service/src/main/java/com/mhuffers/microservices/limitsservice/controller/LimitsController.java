package com.mhuffers.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mhuffers.microservices.limitsservice.bean.LimitConfiguration;
import com.mhuffers.microservices.limitsservice.configuration.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsController {

	@Autowired
	private Configuration Configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsConfiguration(){
		return new LimitConfiguration(Configuration.getMinimum(),Configuration.getMaximum());
	}
	
	@GetMapping("/limits-with-fault-tolerance")
	@HystrixCommand(fallbackMethod="retrieveLimitsWithFallback")
	public LimitConfiguration retrieveLimitsConfigurationWithFaultTolerance(){
		return new LimitConfiguration(Configuration.getMinimum(),Configuration.getMaximum());
	}
	
	public LimitConfiguration retrieveLimitsWithFallback(){
		return new LimitConfiguration(2,2222);
	}
}
