package org.launchcode.stocks.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.launchcode.stocks.entity.Stock;
import org.launchcode.stocks.StockHistory;
import org.launchcode.stocks.entity.StockInfo;
import org.launchcode.stocks.entity.StockIntraday;

@Service
public class StockMarketService {

	@Autowired
	private RestTemplate restTemplate;

	private final static String URL = "https://stock-market-trading-api.herokuapp.com/";

	public StockIntraday fetchIntraday(String symbol) {
		final String uri = URL + "intraday/" + symbol;
		StockIntraday stockResult = restTemplate.getForObject(uri, StockIntraday.class);
		return stockResult;
	}

	public StockHistory fetchHistory(String symbol) {
		final String uri = URL + "history/" + symbol;

		StockHistory stockResult = restTemplate.getForObject(uri, StockHistory.class);
		return stockResult;
	}

	public StockInfo fetchInfo(String symbol) {
		final String uri = URL + "info/" + symbol;
		StockInfo stockResult = restTemplate.getForObject(uri, StockInfo.class);
		return stockResult;
	}
	
	public List<Stock> fetchList() {
		final String uri = URL + "list/";
		Stock[] stocks = restTemplate.getForObject(uri, Stock[].class);
		return Arrays.asList(stocks);
	}

}
