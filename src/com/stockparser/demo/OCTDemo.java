package com.stockparser.demo;

import com.stockparser.dao.TradeRecord;
import com.stockparser.service.OCTStockService;
import com.stockparser.service.StockService;

public class OCTDemo {
	public static void main(String[] args) {
		System.out.println("卷商\t價格\t買入\t賣出");
		
		StockService service = new OCTStockService(5443);
		for(TradeRecord rec : service.getTradeRecords()) {
			System.out.print(rec.getTraderID() + "\t"); // stock trader
			System.out.print(rec.getPrices() + "\t"); // price
			System.out.print(rec.getBuy() + "\t"); // buy
			System.out.print(rec.getSell()); // sell
			System.out.println("");
		}
		
		System.out.println("Total: " + service.getTradeRecords().size() + " Records");
	}
}
