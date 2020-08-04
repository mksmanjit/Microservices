package com.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ExchangeValue {
	
	@Id
	@NonNull
	private Long id;
	
	@Column(name="currency_from")
	@NonNull
	private String from;
	@NonNull
	
	@Column(name="currency_to")
	private String to;
	
	@NonNull
	private BigDecimal conversionMultiple;
	
	private int port;

}
