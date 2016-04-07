package com.nhefner.main;

public class StockHistory {

//	Date,Open,High,Low,Close,Volume,Adj Close
//	2012-10-08,7.38,7.42,7.28,7.32,32324500,6.17507
	private String symbol;
	private String date;
	private double open;
	private double high;
	private double low;
	private double close;
	private double volume;
	private double adjclose;

	public StockHistory(String symbol, String date, double open, double high, double low, double close,
						double volume, double adjclose) {
		this.symbol = symbol; 
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.adjclose = adjclose;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getAdjclose() {
		return adjclose;
	}

	public void setAdjclose(double adjclose) {
		this.adjclose = adjclose;
	}

	@Override
	public String toString() {
		return "StockHistory{" +
				"symbol='" + symbol + '\'' +
				", date='" + date + '\'' +
				", open=" + open +
				", high=" + high +
				", low=" + low +
				", close=" + close +
				", volume=" + volume +
				", adjclose=" + adjclose +
				'}';
	}
}
