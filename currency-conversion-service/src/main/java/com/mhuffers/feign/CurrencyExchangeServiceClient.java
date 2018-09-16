package com.mhuffers.feign;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mhuffers.model.ConversionData;

/*We have multiple configuration options here. First is to hardcode the URL to the currency-exchange-service
 *in the @FeignClient annotation. The other option is to use the @RibbonClient(name="currency-exchange-service")
 *and provide the listOfServers property in the application.properties thereby enabling the RibbonClient to 
 *load balance between the list of added URLs. And the third option is to use a Naming server like Eureka, using this approach 
 *does not involves hard coding the URLs(not even in the properties file) and thus the Ribbon client first registers itself with the Eureka server(if it is a Eureka Client)
 *then fetches all the registered instances with the same application name from Eureka Server and the load balance on its own.
 * */
//@FeignClient(name="currency-exchange-service",url="http://localhost:8001")
//@FeignClient(name="currency-exchange-service")
@FeignClient(name="netflix-zuul-api-gateway-server") //this way all the request made by this feign client will go through the Zuul server
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceClient {

	//@GetMapping("currency-exchange/from/{from}/to/{to}")
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public ConversionData retriveExchangeValues(@PathVariable("from") String from,@PathVariable("to") String to);
	
	
}
