package com.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		Map<String, String> valueMap = new HashMap<>();
		valueMap.put("from", from);
		valueMap.put("to", to);
		ResponseEntity<CurrencyConversionBean> forEntity =	new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, valueMap);
		CurrencyConversionBean currencyConversionBean = forEntity.getBody();
		
		log.info(currencyConversionBean);
		
		return new CurrencyConversionBean(currencyConversionBean.getId(),
				from, to, currencyConversionBean.getConversionMultiple(),
				quantity, quantity.multiply(currencyConversionBean.getConversionMultiple()),
				currencyConversionBean.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		CurrencyConversionBean currencyConversionBean= currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
		log.info(currencyConversionBean);
		
		return new CurrencyConversionBean(currencyConversionBean.getId(),
				from, to, currencyConversionBean.getConversionMultiple(),
				quantity, quantity.multiply(currencyConversionBean.getConversionMultiple()),
				currencyConversionBean.getPort());
	}


}
