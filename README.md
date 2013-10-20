Taiwan Stock Data Fetcher
=====================
Current Version : 0.0.1

## Introduction

Tool to fetch data from [Taiwan Official Stock Site (Listed Companies)](http://bsr.twse.com.tw/bshtm/)

Will provide function for fetching data of OCT companies in the future.

## Usage

You can simply use StockService Class to get a trade records list.

Demo code
```
int stockID = 2330; // TSMC

StockService service = new SIIStockService(stockID);

for(TradeRecord rec : service.getTradeRecords()) {
    System.out.print(rec.getTraderID() + "\t");
	  System.out.print(rec.getPrices() + "\t");
	  System.out.print(rec.getBuy() + "\t");
	  System.out.print(rec.getSell());
	  System.out.println("");			
}
```

## License
 * [MIT](http://opensource.org/licenses/MIT)
