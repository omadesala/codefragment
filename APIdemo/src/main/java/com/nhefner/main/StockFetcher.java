package com.nhefner.main;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

public class StockFetcher {

	public static final String Y = "y";
	public static final String W = "w";
	public static final String D = "d";
	public static final String V = "v";

	// private String url = "http://ichart.yahoo.com/";
	// private String params =
	// "table.csv?s=600000.SS&a=08&b=25&c=2010&d=09&e=8&f=2012&g=d";
	/*
	 * Returns a Stock Object that contains info about a specified stock.
	 * 
	 * @param symbol the company's stock symbol
	 * 
	 * @return a stock object containing info about the company's stock
	 * 
	 * @see Stock
	 */

	static String buildUrlWithParams(String name, String interval,
			int startYear, int startMonth, int startDay, int endYear,
			int endMonth, int endDay) {

		StringBuffer sb = new StringBuffer();

		sb.append("http://ichart.yahoo.com/table.csv?s=").append(name)
				.append("&");
		sb.append("a=");
		sb.append(startMonth + 1);
		sb.append("&");
		sb.append("b=");
		sb.append(startDay);
		sb.append("&");
		sb.append("c=");
		sb.append(startYear);
		sb.append("&");
		sb.append("d=");
		sb.append(endMonth);
		sb.append("&");
		sb.append("e=");
		sb.append(endDay);
		sb.append("&");
		sb.append("f=");
		sb.append(endYear);
		sb.append("&");
		sb.append("g=");
		sb.append(interval);

		return sb.toString();
	}

	static List<StockHistory> getStockHistory(String symbol, DateTime start,
			DateTime stop) {

		// private String symbol;
		String date = "";
		double open = 0;
		double high = 0;
		double low = 0;
		double close = 0;
		double volume = 0;
		double adjclose = 0;

		int startYear = start.getYear();
		int startMonth = start.getMonthOfYear();
		int startDay = start.getDayOfMonth();

		int stopYear = stop.getYear();
		int stopMonth = stop.getMonthOfYear();
		int stopDay = stop.getDayOfMonth();

		System.out.println("start:  " + startYear + " " + startMonth + " "
				+ startDay);
		System.out.println("stop:  " + stopYear + " " + stopMonth + " "
				+ stopDay);

		String url = buildUrlWithParams(symbol, D, startYear, startMonth,
				startDay, stopYear, stopMonth, stopDay);

		List<StockHistory> result = Lists.newLinkedList();
		try {

			// Retrieve CSV File
			URL yahoo = new URL(url);
			System.out.println("url: " + url);
			URLConnection connection = yahoo.openConnection();
			InputStreamReader is = new InputStreamReader(
					connection.getInputStream());
			BufferedReader br = new BufferedReader(is);

			// Parse CSV Into Array

			String line = null;
			br.readLine();

			while ((line = br.readLine()) != null) {

				System.out.println("line: " + line);
				String[] stockinfo = line.split(",");

				// Handle Our Data
				StockHelper sh = new StockHelper();

				date = stockinfo[0];

				open = sh.handleDouble(stockinfo[1]);
				high = sh.handleDouble(stockinfo[2]);
				low = sh.handleDouble(stockinfo[3]);

				close = sh.handleDouble(stockinfo[4]);
				volume = sh.handleDouble(stockinfo[5]);
				adjclose = sh.handleDouble(stockinfo[6]);

				result.add(new StockHistory(symbol, date, open, high, low,
						close, volume, adjclose));

			}

		} catch (IOException e) {
			Logger log = Logger.getLogger(StockFetcher.class.getName());
			log.log(Level.SEVERE, e.toString(), e);
			return null;
		}

		return result;

	}

	static Stock getStock(String symbol) {
		String sym = symbol.toUpperCase();
		double price = 0.0;
		int volume = 0;
		double pe = 0.0;
		double eps = 0.0;
		double week52low = 0.0;
		double week52high = 0.0;
		double daylow = 0.0;
		double dayhigh = 0.0;
		double movingav50day = 0.0;
		double marketcap = 0.0;

		try {

			// Retrieve CSV File
			URL yahoo = new URL("http://finance.yahoo.com/d/quotes.csv?s="
					+ symbol + "&f=l1vr2ejkghm3j3");
			URLConnection connection = yahoo.openConnection();
			InputStreamReader is = new InputStreamReader(
					connection.getInputStream());
			BufferedReader br = new BufferedReader(is);

			// Parse CSV Into Array
			String line = br.readLine();
			String[] stockinfo = line.split(",");

			// Handle Our Data
			StockHelper sh = new StockHelper();

			price = sh.handleDouble(stockinfo[0]);
			volume = sh.handleInt(stockinfo[1]);
			pe = sh.handleDouble(stockinfo[2]);
			eps = sh.handleDouble(stockinfo[3]);
			week52low = sh.handleDouble(stockinfo[4]);
			week52high = sh.handleDouble(stockinfo[5]);
			daylow = sh.handleDouble(stockinfo[6]);
			dayhigh = sh.handleDouble(stockinfo[7]);
			movingav50day = sh.handleDouble(stockinfo[8]);
			marketcap = sh.handleDouble(stockinfo[9]);

		} catch (IOException e) {
			Logger log = Logger.getLogger(StockFetcher.class.getName());
			log.log(Level.SEVERE, e.toString(), e);
			return null;
		}

		return new Stock(sym, price, volume, pe, eps, week52low, week52high,
				daylow, dayhigh, movingav50day, marketcap);

	}
}
