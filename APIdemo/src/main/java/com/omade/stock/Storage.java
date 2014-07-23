package com.omade.stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.common.base.Strings;

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

}
