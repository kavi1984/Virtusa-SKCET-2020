package org.launchcode.stocks.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import org.launchcode.stocks.AnalysisType;
import  org.launchcode.stocks..entity.Candle;
import  org.launchcode.stocks.entity.ResearchData;
import  org.launchcode.stocks.entity.Stock;
import  org.launchcode.stocks.entity.StockHistory;
import  org.launchcode.stocks.entity.StockInfo;
import  org.launchcode.stocks.entity.StockIntraday;
import  org.launchcode.stocks.service.StockMarketService;

import lombok.Getter;

@Controller
@RequestMapping("/stock")
public class StockMarketController {

	final static int FETCH_LAST_YEARS = 1;
	
	@Autowired
	private StockMarketService service;
	
	@Getter
	private StockHistory stockHistory;
	
	@Getter
	private StockIntraday stockIntraday;
	
	@Getter
	private StockInfo stockInfo;
	
	private List<Stock> stocks;
	
	@GetMapping
	public String loadStock(Model model) {
		this.stocks = service.fetchList();
		model.addAttribute("researchData", new ResearchData()); 
		model.addAttribute("stocks", stocks);
		model.addAttribute("stockInfo", new StockInfo());
		model.addAttribute("stockHistory", null);
        model.addAttribute("stockIntraday", null);
		return "stock";
	}
	
    @PostMapping
    public String loadStock(Model model, @Valid @ModelAttribute ResearchData researchData) {
    	Gson gson = new Gson();
    	List<Candle> intraday = new ArrayList<>();
    	List<Candle> history = new ArrayList<>();
    	List<Candle> filteredHistory = new ArrayList<>();  	
    	stockInfo = service.fetchInfo(researchData.getSymbol());
    	researchData.setAnalysisType(AnalysisType.HISTORY);	
    	model.addAttribute("stockInfo", stockInfo);
    	if(researchData.getAnalysisType().equals(AnalysisType.INTRADAY)) {
    		stockIntraday = service.fetchIntraday(researchData.getSymbol());
        	intraday = stockIntraday.getIntraday().values().stream().collect(Collectors.toList());
    	} else {
    		stockHistory = service.fetchHistory(researchData.getSymbol());
    		history = stockHistory.getHistory().values().stream().collect(Collectors.toList());
    		filteredHistory = this.filterLastFiveYears(history, FETCH_LAST_YEARS);
    	}   	   	
        model.addAttribute("stockHistory", gson.toJson(filteredHistory));
        model.addAttribute("stockIntraday", gson.toJson(intraday));
        model.addAttribute("researchData", researchData); 
		model.addAttribute("stocks", stocks);
        return "stock";
    }

	private List<Candle> filterLastFiveYears(List<Candle> history, Integer lastYears) {
		return history.stream().filter(h -> this.belongsToLastYears(h.getDate(), lastYears)).collect(Collectors.toList());
	}

	private boolean belongsToLastYears(String date,  Integer lastYears) {
		boolean belongs = false;
		LocalDate now = LocalDate.now();
		int currentYear = now.getYear();
		for (int i = 0; i < lastYears; i++) {
			Integer year = currentYear - i;
			if(date.contains(year.toString())) {
				belongs = true;
			}
		}
		return belongs;
	}
	
}
