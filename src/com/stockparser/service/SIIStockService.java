package com.stockparser.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.stockparser.dao.TradeRecord;

public class SIIStockService implements StockService {
	
	private int stockID;
	private String pageSite = "http://bsr.twse.com.tw/bshtm/bsMenu.aspx?";
	private List<TradeRecord> tradeRecords = new ArrayList<TradeRecord>();
	
	public SIIStockService(int stockID) {
		this.stockID = stockID;
	}

	@Override
	public void refreshRecords() {
		
		tradeRecords.clear();
		
		int pageNum = getPageNum();
		if(pageNum == -1) {
			System.out.println("Error While Querying Page For Stock " + stockID);
			return;
		}
		
		String target = "http://bsr.twse.com.tw/bshtm/bsContent.aspx?StartNumber=" + stockID + "&FocusIndex=All_" + pageNum;
		
		try {
			File tmpFile = File.createTempFile("stock",".tmp");
			FileUtils.copyURLToFile(new URL(target), tmpFile);
			
			Document doc = Jsoup.parse(tmpFile, "UTF-8");
			
			Elements prices_3 = doc.select(".column_value_price_3");
			ListIterator<Element> iter = prices_3.listIterator();
			while(iter.hasNext()) {
				
				Element e = iter.next();
				
				if(e.child(0).html().equals("&nbsp;")) continue;
				
				int buy = Integer.parseInt(e.child(3).text().replace(",", ""));
				int sell = Integer.parseInt(e.child(4).text().replace(",", ""));
				double price = Double.parseDouble(e.child(2).text());
				String[] tmpTrade = e.child(1).text().split(" ");
				
				TradeRecord tRec = new TradeRecord();
				tRec.setStockID(stockID);
				tRec.setTraderID(tmpTrade[0]);
				tRec.setPrices(price);
				tRec.setBuy(buy);
				tRec.setSell(sell);
				
				tradeRecords.add(tRec);
			}
			
			Elements prices_2 = doc.select(".column_value_price_2");
			iter = prices_2.listIterator();
			while(iter.hasNext()) {
				
				Element e = iter.next();
				
				if(e.child(0).html().equals("&nbsp;")) continue;
				
				int buy = Integer.parseInt(e.child(3).text().replace(",", ""));
				int sell = Integer.parseInt(e.child(4).text().replace(",", ""));
				double price = Double.parseDouble(e.child(2).text());
				
				TradeRecord tRec = new TradeRecord();
				tRec.setStockID(stockID);
				tRec.setTraderID(e.child(1).text());
				tRec.setBuy(buy);
				tRec.setSell(sell);
				tRec.setPrices(price);
				
				tradeRecords.add(tRec);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<TradeRecord> getTradeRecords() {
		
		if(this.tradeRecords.size() == 0)
			refreshRecords();
		
		return this.tradeRecords;	
	}
	
	public int getPageNum() {
		
		ArrayList<NameValuePair> pairList = new ArrayList<NameValuePair>();
		pairList.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUKLTQzNzI3ODE3MQ9kFgICAQ9kFg4CBQ8WAh4JaW5uZXJodG1sBQoyMDEzLzEwLzE4ZAIGDxYCHwAFCDIwMTMxMDE4ZAIIDw8WBh4JRm9udF9Cb2xkZx4EXyFTQgKEEB4JRm9yZUNvbG9yCj1kZAIKD2QWAgIBDw9kFgIeB09uQ2xpY2sFHGphdmFzY3JpcHQ6YnV0Q2xlYXJfQ2xpY2soKTtkAgwPDxYGHwFoHwIChBAfAwpHZGQCDg8PFgIeB1Zpc2libGVoZGQCEA8PFgYfAWgfAgKEEB8DCkdkZGQMvN4MHMFDndGdrNS7KK/qAAAAAA=="));
		pairList.add(new BasicNameValuePair("HiddenField_page", "PAGE_BS"));
		pairList.add(new BasicNameValuePair("txtTASKNO", String.valueOf(stockID)));
		pairList.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWCQLNi/2dBALjpuXcAwKN4Ij0CwLB5ZfoCQLjk6TKBwKY8en5CwLdkpmPAQL6n7vzCwLAhrvLBbRUGtPaMIbMElhBJVK3BuUAAAAA"));
		pairList.add(new BasicNameValuePair("btnOK", "¬d¸ß"));
		
		HttpPost httpPost = new HttpPost(pageSite);
		httpPost.setHeader("Host", "bsr.twse.com.tw");
		httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.setHeader("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");		
		
		StringEntity entity;
		try {
			entity = new StringEntity(URLEncodedUtils.format(pairList, "UTF-8"));
			
			httpPost.setEntity(entity);
		
			HttpClient httpclient = HttpClientBuilder.create().build();	
			HttpResponse response = httpclient.execute(httpPost);
			String responseString = EntityUtils.toString(response.getEntity());
			
			Document doc = Jsoup.parse(responseString);
			Element pageNum = doc.select("#sp_ListCount").first();
			
			return Integer.parseInt(pageNum.text());
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
}