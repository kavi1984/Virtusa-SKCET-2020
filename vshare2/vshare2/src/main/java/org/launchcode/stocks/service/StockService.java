package org.launchcode.stocks.services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.launchcode.stocks.models.Stock1;
import org.launchcode.stocks.repositories.StockRepository;


@Service
public class StockService {
	
	@Autowired 
	private StockRepository stockRepository;

	public Collection<Stock1> getAllStocks() {
		Collection<Stock1> stocks = stockRepository.findAll();
		return stocks;
	}

	public Stock1 getStockById(Long id) {
		return stockRepository.findById(id);
	}

	public void saveStock(Stock1 stock) {
		stock.setId(null);
		stock.setLastUpdatedAt(new Date());
		stockRepository.save(stock);
	}

	public void editStock(Stock1 editedStock) {
		editedStock.setLastUpdatedAt(new Date());
		stockRepository.saveAndFlush(editedStock);
	}

	public void deleteStock(Long id) {
		stockRepository.delete(id);
	}
		
}