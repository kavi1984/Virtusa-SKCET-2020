package org.launchcode.stocks.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StockInfo {

	private String symbol;
	private String name;
	private String currency;
	private String price;
	private String volume;
	private String shares;
	private String timezone;

	@JsonProperty("price_open")
	private String priceOpen;

	@JsonProperty("day_high")
	private String dayHigh;

	@JsonProperty("day_low")
	private String dayLow;

	@JsonProperty("52_week_high")
	private String weekHigh52;

	@JsonProperty("52_week_low")
	private String weekLow52;

	@JsonProperty("day_change")
	private String dayChange;

	@JsonProperty("change_pct")
	private String changePct;

	@JsonProperty("close_yesterday")
	private String closeYesterday;

	@JsonProperty("market_cap")
	private String marketCap;

	@JsonProperty("volume_avg")
	private String volumeAvg;

	@JsonProperty("stock_exchange_long")
	private String stockExchangeLong;

	@JsonProperty("stock_exchange_short")
	private String stockExchangeShort;

	@JsonProperty("timezone_name")
	private String timezoneName;

	@JsonProperty("gmt_offset")
	private String gmtOffset;

	@JsonProperty("last_trade_time")
	private String lastTradeTime;

}
