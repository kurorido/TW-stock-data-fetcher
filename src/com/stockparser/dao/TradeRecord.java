package com.stockparser.dao;

import java.util.Date;

public class TradeRecord {
	
	private String stockID;
	private String traderID;
	private double prices;
	private int buy;
	private int sell;
	private Date date;
	
	public String getStockID() {
		return stockID;
	}
	
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	
	public String getTraderID() {
		return traderID;
	}

	public void setTraderID(String traderID) {
		this.traderID = traderID;
	}

	public double getPrices() {
		return prices;
	}
	
	public void setPrices(double prices) {
		this.prices = prices;
	}
	
	public int getBuy() {
		return buy;
	}
	
	public void setBuy(int buy) {
		this.buy = buy;
	}
	
	public int getSell() {
		return sell;
	}
	
	public void setSell(int sell) {
		this.sell = sell;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
