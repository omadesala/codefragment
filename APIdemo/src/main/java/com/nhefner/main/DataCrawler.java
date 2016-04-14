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
		start(symbols);

	}

	public static void start(List<String> symbols) {

		try {
			DateTime start = new DateTime("2012-01-01");
			DateTime end = new DateTime("2013-01-01");
			for (String symbol : symbols) {

				List<StockHistory> history = StockFetcher.getStockHistory(
						symbol, start, end);
				DataETLUtil.save(history);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
