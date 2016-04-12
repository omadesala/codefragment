package com.omade.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.chinacloud.blackhole.db.DBPojo;
import com.chinacloud.blackhole.db.MySqlManager;

public class YahooStock {

    public static MySqlManager mysql;
    public static DBPojo db;

    public static Storage storage;

    public static final String YAHOO_FINANCE_URL = "http://table.finance.yahoo.com/table.csv?";
    public static final String YAHOO_FINANCE_URL_TODAY = "http://download.finance.yahoo.com/d/quotes.csv?";

    static {
        db = new DBPojo();
        db.setDbtype("mysql");
        db.setInstance("stocks");
        db.setIp("localhost");
        db.setPort("3306");
        db.setTableName("test");
        db.setPwd("123456");
        db.setUser("root");
        mysql = new MySqlManager(db);
        storage = new Storage(mysql);

    }

    public static List<StockData> getStockCsvData(String stockName, String fromDate, String toDate) {
        List<StockData> list = new ArrayList<StockData>();
        String[] datefromInfo = fromDate.split("-");
        String[] toDateInfo = toDate.split("-");
        // String code = stockName.substring(0, 6);;
        String code = "";

        String a = (Integer.valueOf(datefromInfo[1]) - 1) + "";// a – start
                                                               // month
        String b = datefromInfo[2];// b – start day
        String c = datefromInfo[0];// c – start year
        String d = (Integer.valueOf(toDateInfo[1]) - 1) + "";// d – end month
        String e = toDateInfo[2];// e – end day
        String f = toDateInfo[0];// f – end year

        String params = "&a=" + a + "&b=" + b + "&c=" + c + "&d=" + d + "&e=" + e + "&f=" + f;
        String url = YAHOO_FINANCE_URL + "s=" + stockName + params;

        System.out.println("url: " + url);
        URL MyURL = null;
        URLConnection con = null;
        InputStreamReader ins = null;
        BufferedReader in = null;
        try {

            MyURL = new URL(url);
            // 创建代理服务器
            InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 8087);
            // Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr); // Socket 代理
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
            // 如果我们知道代理server的名字, 可以直接使用
            // 结束
            con = MyURL.openConnection(proxy);
            ins = new InputStreamReader(con.getInputStream(), "UTF-8");
            in = new BufferedReader(ins);
            // in = con.getInputStream();

            // MyURL = new URL(url);
            // con = MyURL.openConnection();
            // ins = new InputStreamReader(con.getInputStream(), "UTF-8");
            // in = new BufferedReader(ins);

            String newLine = in.readLine();// the first line is title

            while ((newLine = in.readLine()) != null) {

                System.out.println("new line: " + newLine);
                String stockInfo[] = newLine.trim().split(",");
                StockData sd = new StockData();
                sd.setName(stockName);
                sd.setCode(stockName);
                sd.setDate(stockInfo[0]);
                sd.setOpen(Float.valueOf(stockInfo[1]));
                sd.setHigh(Float.valueOf(stockInfo[2]));
                sd.setLow(Float.valueOf(stockInfo[3]));
                sd.setClose(Float.valueOf(stockInfo[4]));
                sd.setVolume(Float.valueOf(stockInfo[5]));
                sd.setAdj(Float.valueOf(stockInfo[6]));
                list.add(sd);
            }

        } catch (Exception ex) {
            System.out.println("no data received");
            return null; //
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
        return list;
    }

    public static StockData getStockCsvData(String stockName, String date) {
        List<StockData> list = getStockCsvData(stockName, date, date);
        return ((list.size() > 0) ? list.get(0) : null);
    }

    /**
     * 
     */
    public static StockData getStockCsvData(String stockName) {
        String date = String.format("%1$tF", new Date());
        List<StockData> list = getStockCsvData(stockName, date, date);
        return ((list != null && list.size() > 0) ? list.get(0) : null);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        YahooStock stockUtil = new YahooStock();
        // StockData sd = stockUtil.getStockCsvData("600629.ss", "2012-12-31");
        // System.out.println(sd.toString());
        // StockData sd = stockUtil.getStockCsvData("vips", "2014-07-15");

        storage.connect();

        String tableName = "vips";

        Date lastRecordDate = storage.getLastRecordByColumn(tableName);

        DateTime dt = new DateTime(lastRecordDate);
        DateTime plusDays = dt.plusDays(1);

        int loopcount = 0;
        List<StockData> stockCsvData = null;
        do {
            String parseDate2Str = Utils.parseDate2Str(plusDays.toDate());
            System.out.println("last day: " + parseDate2Str);
            String today = Utils.parseDate2Str(new Date());
            System.out.println("today: " + today);

            stockCsvData = stockUtil.getStockCsvData("VIPS", parseDate2Str, today);
            if (loopcount > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (stockCsvData == null && loopcount++ < 3);

        for (StockData stockData : stockCsvData) {
            System.out.println(stockData.toString());
            if (!storage.isTableExists(tableName)) {
                storage.createTable(tableName);
            }

            storage.insert(stockData, tableName);
        }

        storage.close();

    }

    public List<StockData> readData(String tableName, Date from, int length) {
        List<StockData> records = storage.getRecord(tableName, Utils.parseStr2Date("2013-07-26"), 3);
        Utils.printInfo(records);
        return records;
    }

}
