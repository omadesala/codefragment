package com.omade.stock;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.common.base.Strings;
import org.elasticsearch.common.collect.Lists;
import org.joda.time.DateTime;

import com.chinacloud.blackhole.db.MySqlManager;
import com.google.common.base.Preconditions;

public class Storage {

    private MySqlManager mysql;

    public Storage(MySqlManager mysqlmanager) {

        this.mysql = mysqlmanager;
    }

    public void connect() {
        try {
            mysql.Connection();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {

        mysql.closeConnection();
    }

    public void createTable(String tableName) {

        if (!isTableExists(tableName)) {

            String sql = "create table " + tableName + " (" + "code VARCHAR(20)," + "name VARCHAR(20)," + "date DATE,"
                    + "open DOUBLE," + "high DOUBLE," + "low DOUBLE," + "close DOUBLE," + "volume DOUBLE,"
                    + "adj DOUBLE" + ");";
            System.out.println("sql: " + sql);
            this.mysql.executeSQL(sql);
        }

    }

    public boolean isTableExists(String tableName) {

        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name=" + "'" + tableName + "';";
        System.out.println("sql: " + sql);

        ResultSet rs = this.mysql.QuerySQL(sql);

        try {
            rs.last();
            int rowCount = rs.getRow();
            if (rowCount == 0)
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean insert(StockData sd, String tableName) {

        Preconditions.checkArgument(sd != null, "input should not be null");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(tableName), "input should not be null");

        // DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format1 = sdf;
        Date date = null;
        try {
            date = sdf.parse(sd.getDate());
            System.out.println(date.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String sql = "insert into " + tableName + " (code,name,date,open,high,low,close,volume,adj) " + "values('"
                + sd.getCode() + "','" + sd.getName() + "','" + format1.format(date) + "'," + sd.getOpen() + ","
                + sd.getHigh() + "," + sd.getLow() + "," + sd.getClose() + "," + sd.getVolume() + "," + sd.getAdj()
                + ");";

        System.out.println("sql: " + sql);
        return this.mysql.executeSQL(sql);

    }

    public Date getLastRecordByColumn(String tableName) {

        Preconditions.checkArgument(!Strings.isNullOrEmpty(tableName));

        String sql = "select * from " + tableName + " order by date limit 1;";
        System.out.println("sql : " + sql);

        ResultSet rs = mysql.QuerySQL(sql);
        try {
            rs.last();
            int row = rs.getRow();
            if (row != 1) {
                // return a lastyear ago
                DateTime dateTime = new DateTime(new Date());
                return dateTime.minusYears(1).toDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs.first();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                // String columnTypeName = metaData.getColumnTypeName(i);
                // System.out.println("columnTypeName" + columnTypeName);
                if (columnName.equals("date")) {
                    return rs.getDate(i + 1);
                    // return Utils.parseDate2Str(rs.getDate(i));
                }
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return null;

    }

    public List<StockData> getRecord(String tableName, Date from, int length) {

        Preconditions.checkArgument(!Strings.isNullOrEmpty(tableName));
        Preconditions.checkArgument(from != null);
        Preconditions.checkArgument(length >= 0);

        Date enddate = new DateTime(from).plusDays(length).toDate();

        String sql = "select * from " + tableName + " where date >= '" + Utils.parseDate2Str(from) + "'and date <='"
                + Utils.parseDate2Str(enddate) + "';";
        System.out.println("sql : " + sql);

        ResultSet rs = mysql.QuerySQL(sql);

        List<StockData> results = Lists.newArrayList();

        try {
            while (rs.next()) {

                StockData sd = new StockData();

                sd.setCode(rs.getString(1));
                sd.setName(rs.getString(2));
                sd.setDate(Utils.parseDate2Str(rs.getDate(3)));
                sd.setOpen(rs.getDouble(4));
                sd.setHigh(rs.getDouble(5));
                sd.setLow(rs.getDouble(6));
                sd.setClose(rs.getDouble(7));
                sd.setVolume(rs.getDouble(8));
                sd.setAdj(rs.getDouble(9));

                results.add(sd);
            }
        } catch (SQLException e) {

            e.printStackTrace();

        }

        return results;
    }
}
