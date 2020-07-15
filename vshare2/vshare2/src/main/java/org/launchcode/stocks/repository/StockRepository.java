package org.launchcode.stocks.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.launchcode.stocks.models.Stock1;

public interface StockRepository extends JpaRepository<Stock, Long> {
	
	public List<Stock> findAll();
	
	public Stock findById(Long id);

}
