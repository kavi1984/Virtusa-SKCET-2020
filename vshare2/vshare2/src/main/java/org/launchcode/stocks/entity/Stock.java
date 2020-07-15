package org.launchcode.stocks.entity;

import lombok.Data;

@Data
public class Stock {

	private String symbol;
	private String name;
	private String currency;
	private String stockExchangeLong;
	private String stockExchangeShort;
	private String timezoneName;
	
}
