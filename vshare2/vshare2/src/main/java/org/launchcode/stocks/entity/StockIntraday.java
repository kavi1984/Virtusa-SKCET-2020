package org.launchcode.stocks.entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StockIntraday {

	@JsonProperty("stock_exchange_short")
	private String stockExchangeShort;
	
	@JsonProperty("timezone_name")
	private String timezoneName;

	private String symbol;
	private Map<String, Candle> intraday;
	
}
