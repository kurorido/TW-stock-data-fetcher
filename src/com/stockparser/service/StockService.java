package com.stockparser.service;

import java.util.List;

import com.stockparser.dao.TradeRecord;

public interface StockService {
	
	public List<TradeRecord> getTradeRecords();
	public void refreshRecords();

}
