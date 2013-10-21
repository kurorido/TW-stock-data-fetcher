package com.stockparser.demo;

import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;

import au.com.bytecode.opencsv.CSVReader;

import com.stockparser.dao.TradeRecord;
import com.stockparser.util.DateUtil;

public class Test {

	private static List<TradeRecord> tradeRecords = new ArrayList<TradeRecord>();
	private static int stockID = 5443;
	
	public static void main(String[] args) {
		
		System.out.println(DateUtil.getNowDateString());
		
//		CSVReader reader = null;
//		try {
//
//			int count = 0;
//			reader = new CSVReader(new FileReader(Test.class.getResource("/com/stockparser/resources/5443_1021018.csv").getPath()));
//			String [] nextLine;
//			while ((nextLine = reader.readNext()) != null) {
//
//				if (count < 3) { // skip first three line.
//					count++;
//					continue;
//				}
//
//				String[] tmpTrade = nextLine[1].split(" ");
//				double price = Double.parseDouble(nextLine[2].replace(",", ""));
//				int buy = Integer.parseInt(nextLine[3].replace(",", ""));
//				int sell = Integer.parseInt(nextLine[4].replace(",", ""));
//
//				TradeRecord tRec1 = new TradeRecord();
//				tRec1.setStockID(stockID);
//				tRec1.setTraderID(tmpTrade[0]);
//				tRec1.setPrices(price);
//				tRec1.setBuy(buy);
//				tRec1.setSell(sell);
//
//				tradeRecords.add(tRec1);
//				
//				if(nextLine.length < 6) continue;
//
//				tmpTrade = nextLine[7].split(" ");
//				price = Double.parseDouble(nextLine[8].replace(",", ""));
//				buy = Integer.parseInt(nextLine[9].replace(",", ""));
//				sell = Integer.parseInt(nextLine[10].replace(",", ""));
//
//				TradeRecord tRec2 = new TradeRecord();
//				tRec2.setStockID(stockID);
//				tRec2.setTraderID(tmpTrade[0]);
//				tRec2.setPrices(price);
//				tRec2.setBuy(buy);
//				tRec2.setSell(sell);
//
//				tradeRecords.add(tRec2);
//			}
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if(reader != null)
//				try {
//					reader.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//		
//		System.out.println(tradeRecords.size());
	}

}
