package com.stockparser.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import au.com.bytecode.opencsv.CSVReader;

import com.stockparser.dao.TradeRecord;

public class OTCStockService implements StockService {

	private int stockID;
	private String target = "http://www.gretai.org.tw/ch/stock/aftertrading/broker_trading/download_ALLCSV.php";
	private List<TradeRecord> tradeRecords = new ArrayList<TradeRecord>();
	
	public OTCStockService(int stockID) {
		this.stockID = stockID;
	}
	
	@Override
	public List<TradeRecord> getTradeRecords() {
		if(this.tradeRecords.size() == 0)
			refreshRecords();
		
		return this.tradeRecords;
	}

	@Override
	public void refreshRecords() {
		
		tradeRecords.clear();
		
		ArrayList<NameValuePair> pairList = new ArrayList<NameValuePair>();
		pairList.add(new BasicNameValuePair("curstk", String.valueOf(stockID)));
		pairList.add(new BasicNameValuePair("fromw", "0"));
		pairList.add(new BasicNameValuePair("numbern", "100"));
		pairList.add(new BasicNameValuePair("stk_date", "1021021"));
		
		HttpPost httpPost = new HttpPost(target);
		httpPost.setHeader("Host", "www.gretai.org.tw");
		httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.setHeader("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "Mozilla/5.0");	
		
		StringEntity entity;
		CSVReader reader = null;
		try {
			entity = new StringEntity(URLEncodedUtils.format(pairList, "UTF-8"));
			
			httpPost.setEntity(entity);

			HttpClient httpclient = HttpClientBuilder.create().build();	
			HttpResponse response = httpclient.execute(httpPost);
			
			File tmpFile = File.createTempFile("stock",".tmp");
			FileUtils.copyInputStreamToFile(response.getEntity().getContent(), tmpFile);
			
			int count = 0;
			reader = new CSVReader(new FileReader(tmpFile));
			String [] nextLine;
			while ((nextLine = reader.readNext()) != null) {

				if (count < 3) { // skip first three line.
					count++;
					continue;
				}

				String[] tmpTrade = nextLine[1].split(" ");
				double price = Double.parseDouble(nextLine[2].replace(",", ""));
				int buy = Integer.parseInt(nextLine[3].replace(",", ""));
				int sell = Integer.parseInt(nextLine[4].replace(",", ""));

				TradeRecord tRec1 = new TradeRecord();
				tRec1.setStockID(stockID);
				tRec1.setTraderID(tmpTrade[0]);
				tRec1.setPrices(price);
				tRec1.setBuy(buy);
				tRec1.setSell(sell);

				tradeRecords.add(tRec1);
				
				if(nextLine.length < 6) continue;

				tmpTrade = nextLine[7].split(" ");
				price = Double.parseDouble(nextLine[8].replace(",", ""));
				buy = Integer.parseInt(nextLine[9].replace(",", ""));
				sell = Integer.parseInt(nextLine[10].replace(",", ""));

				TradeRecord tRec2 = new TradeRecord();
				tRec2.setStockID(stockID);
				tRec2.setTraderID(tmpTrade[0]);
				tRec2.setPrices(price);
				tRec2.setBuy(buy);
				tRec2.setSell(sell);

				tradeRecords.add(tRec2);
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
