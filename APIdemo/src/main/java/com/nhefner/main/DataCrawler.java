package com.nhefner.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;
import com.omade.utils.DataETLUtil;

public class DataCrawler {

	public static void main(String[] args) {

		List<String> symbols = Lists.newArrayList();
		symbols.add("IBM");
		DateTime start = new DateTime("2011-01-01");
		DateTime end = new DateTime("2014-01-01");

		for (String symbol : symbols) {
			start(symbols, start, end);
		}

	}

	public static void start(List<String> symbols, DateTime start, DateTime end) {

		try {
			for (String symbol : symbols) {

				List<StockHistory> history = StockFetcher.getStockHistory(
						symbol, start, end);
				DataETLUtil.save(history);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
