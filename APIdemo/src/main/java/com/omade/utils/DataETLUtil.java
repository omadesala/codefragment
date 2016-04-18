package com.omade.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.chinacloud.blackhole.db.DBPojo;
import com.chinacloud.blackhole.db.MySqlManager;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.nhefner.main.StockHistory;

public class DataETLUtil {

    public static MySqlManager getDBManager() {

        DBPojo db = new DBPojo();
        db.setDbtype("mysql");
        db.setInstance("stocks");
//		db.setIp("172.16.50.80");
        db.setIp("localhost");
        db.setPort("3306");
        // db.setTableName("600000SS");
        db.setUser("root");
        db.setPwd("root");
//		db.setPwd("hadoop");

        MySqlManager mySqlManager = new MySqlManager(db);
        try {
            mySqlManager.Connection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }

        return mySqlManager;
    }

    public static void clean(String srctableName, String dstTableName,
                             int window) {

        MySqlManager dbManager = DataETLUtil.getDBManager();

        String droptable = "drop table if exists " + dstTableName;
        dbManager.executeSQL(droptable);
        StringBuffer sql = new StringBuffer();

        sql.append("CREATE TABLE ").append(dstTableName).append("(");

        List<String> colName = Lists.newArrayList();
        for (int i = 0; i < window; i++) {
            colName.add("p" + (i + 1) + " double");
        }

        colName.add("target double");
        sql.append(Joiner.on(",").join(colName)).append(")");

        System.out.println("create table sql : " + sql.toString());

        dbManager.executeSQL(sql.toString());

        ResultSet querySQL = dbManager.QuerySQL("select count(*) from "
                + srctableName);
        List<List<String>> rows = DataETLUtil.getRows(querySQL);
        int count = Integer.parseInt(rows.get(0).get(0));

        StringBuffer sb = new StringBuffer();
        sb.append("insert into ").append(dstTableName).append("(");
        List<String> values = Lists.newArrayList();
        for (int i = 0; i < window; i++) {
            values.add("p" + (i + 1));
        }
        values.add("target");

        sb.append(Joiner.on(",").join(values)).append(") values ");

        for (int j = 0, len = count - window; j < len; j++) {

            String selectsql = "select close from " + srctableName + " limit "
                    + j + "," + (window + 1);
            System.out.println("sql : " + selectsql);
            ResultSet rs = dbManager.QuerySQL(selectsql);
            List<List<String>> datarows = DataETLUtil.getRows(rs);

            sb.append("(");
            List<String> rowdata = datarows.get(0);
            String join = Joiner.on(",").join(rowdata);
            sb.append(join).append(")");

            if (j != len - 1)
                sb.append(",");
        }

        System.out.println("sql : " + sb.toString());

        dbManager.executeSQL(sb.toString());
        dbManager.closeConnection();
    }

    public static void cleanNormalize(String srctableName, String dstTableName,
                                      int window) {

        MySqlManager dbManager = DataETLUtil.getDBManager();

        String droptable = "drop table if exists " + dstTableName;
        dbManager.executeSQL(droptable);
        StringBuffer sql = new StringBuffer();

        sql.append("CREATE TABLE ").append(dstTableName).append("(");

        List<String> colName = Lists.newArrayList();
        for (int i = 0; i < window; i++) {
            colName.add("p" + (i + 1) + " double");
        }

        colName.add("target double");
        sql.append(Joiner.on(",").join(colName)).append(")");

        System.out.println("create table sql : " + sql.toString());

        dbManager.executeSQL(sql.toString());

        ResultSet querySQL = dbManager.QuerySQL("select count(*) from "
                + srctableName);
        List<List<String>> rows = DataETLUtil.getRows(querySQL);
        int count = Integer.parseInt(rows.get(0).get(0));

        StringBuffer sb = new StringBuffer();
        sb.append("insert into ").append(dstTableName).append("(");
        List<String> values = Lists.newArrayList();
        for (int i = 0; i < window; i++) {
            values.add("p" + (i + 1));
        }
        values.add("target");

        sb.append(Joiner.on(",").join(values)).append(") values ");

        for (int j = 0, len = count - window; j < len; j++) {

            String selectsql = "select close from " + srctableName + " limit "
                    + j + "," + (window + 1);
            System.out.println("sql : " + selectsql);
            ResultSet rs = dbManager.QuerySQL(selectsql);
            List<List<String>> datarows = DataETLUtil.getRows(rs);

            sb.append("(");
            List<String> rowdata = datarows.get(0);
            double total = 0.0;
            for (String price : rowdata) {
                double v = Double.parseDouble(price);
                total += v;
            }

            double mean = total / rowdata.size();
            List<Double> normalize = Lists.newArrayList();
            double var = 0.0;
            for (String price : rowdata) {
                double v = Double.parseDouble(price);
                var += (v - mean) * (v - mean);
            }

            var = Math.sqrt(var / rowdata.size());

            for (String price : rowdata) {
                double v = Double.parseDouble(price);
                normalize.add((v - mean) / var);
            }


            String join = Joiner.on(",").join(normalize);
//            String join = Joiner.on(",").join(rowdata);
            sb.append(join).append(")");

            if (j != len - 1)
                sb.append(",");
        }

        System.out.println("sql : " + sb.toString());

        dbManager.executeSQL(sb.toString());
        dbManager.closeConnection();
    }

    public static void save(List<StockHistory> data) {

        Preconditions.checkArgument(data != null && data.size() > 0);

        MySqlManager dbManager = DataETLUtil.getDBManager();
        String symbol = data.get(0).getSymbol();
        String droptable = "drop table if exists " + symbol;
        dbManager.executeSQL(droptable);
        String sql = "CREATE TABLE "
                + symbol
                + " ( date date DEFAULT NULL, open double DEFAULT NULL,  high double DEFAULT NULL,  low double DEFAULT NULL,  close double DEFAULT NULL,  volume double DEFAULT NULL,  adjclose double DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8";

        System.out.println("create sql : " + sql);

        dbManager.executeSQL(sql);

        StringBuffer insertsql = new StringBuffer();
        insertsql
                .append("insert into ")
                .append(symbol)
                .append("( date, open,  high ,  low,  close,  volume,  adjclose)")
                .append(" values");

        List<String> values = Lists.newArrayList();
        for (StockHistory item : data) {

            StringBuffer sb = new StringBuffer();
            sb.append("('").append(item.getDate()).append("',")
                    .append(item.getOpen()).append(",").append(item.getHigh())
                    .append(",").append(item.getLow()).append(",")
                    .append(item.getClose()).append(",")
                    .append(item.getVolume()).append(",")
                    .append(item.getAdjclose()).append(")");

            values.add(sb.toString());
        }

        String joinValues = Joiner.on(",").join(values);
        insertsql.append(joinValues);

        System.out.println("insert sql : " + insertsql.toString());
        dbManager.executeSQL(insertsql.toString());

    }

    public static void load() {

    }

    public static void main(String[] args) {

        // clean("600000SS", "600000SS11", 10);
        String tableName = "IBM";
        int interval = 10;
//        clean(tableName, tableName + "11", interval);
        cleanNormalize(tableName, tableName + "11Norm", interval);
    }

    public static List<List<String>> getRows(ResultSet rs) {

        List<List<String>> rows = Lists.newArrayList();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> row = Lists.newArrayList();
            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    row.add(rs.getString(i + 1));
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
