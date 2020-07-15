package org.launchcode.stocks.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class Stock1 {
	
	@Id @GeneratedValue
	private Long id;
	
	@NotNull
	@Size(min=2, max=30)
	private String name;
	
	@NotNull
	private Double currentPrice;
	
	@NotNull
	@JsonFormat(pattern="E, dd MMM yyyy HH:mm:ss z")
	private Date lastUpdatedAt;

	public Stock () {
		
	}
	
	public Stock(Long id, String name, Double currentPrice, Date lastUpdatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.currentPrice = currentPrice;
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	
	
	

	

}
