Taiwan Stock Data Fetcher
=====================
Current Version : 0.8.0

## Introduction

Tool to fetch data for Taiwan Stock.

Support SII and OCT

[Taiwan Official Stock Site (Listed Companies)](http://bsr.twse.com.tw/bshtm/)

[OTC Official Site (Over The Counter)](http://www.gretai.org.tw/ch/index.php)

## Usage

You can simply use StockService Class to get a trade records list.

Demo code
```
String stockID = "2330"; // TSMC

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
