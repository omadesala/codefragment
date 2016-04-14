package com.nhefner.main;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class StockTest {

	@Test
	public void testStock() {

		Stock facebook = StockFetcher.getStock("FB");
		System.out.println("Price: " + facebook.getPrice());
		System.out.println("Volume: " + facebook.getVolume());
		System.out.println("P/E: " + facebook.getPe());
		System.out.println("EPS: " + facebook.getEps());
		System.out.println("Year Low: " + facebook.getWeek52low());
		System.out.println("Year High: " + facebook.getWeek52high());
		System.out.println("Day Low: " + facebook.getDaylow());
		System.out.println("Day High: " + facebook.getDayhigh());
		System.out.println("50 Day Moving Av: " + facebook.getMovingav50day());
		System.out.println("Market Cap: " + facebook.getMarketcap());
	}

	@Test
	public void testStockHistory() {

		List<StockHistory> history = StockFetcher.getStockHistory("600000.SS",
				new DateTime("2012-01-01"), new DateTime("2013-01-01"));
		System.out.println("result:" + history.toString());

		// System.out.println("Price: " + facebook.getPrice());
		// System.out.println("Volume: " + facebook.getVolume());
		// System.out.println("P/E: " + facebook.getPe());
		// System.out.println("EPS: " + facebook.getEps());
		// System.out.println("Year Low: " + facebook.getWeek52low());
		// System.out.println("Year High: " + facebook.getWeek52high());
		// System.out.println("Day Low: " + facebook.getDaylow());
		// System.out.println("Day High: " + facebook.getDayhigh());
		// System.out.println("50 Day Moving Av: " +
		// facebook.getMovingav50day());
		// System.out.println("Market Cap: " + facebook.getMarketcap());
	}

}
