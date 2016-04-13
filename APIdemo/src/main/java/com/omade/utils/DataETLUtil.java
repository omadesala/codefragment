package com.omade.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.chinacloud.blackhole.db.DBPojo;
import com.chinacloud.blackhole.db.MySqlManager;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class DataETLUtil {

	public static MySqlManager getDBManager() {

		DBPojo db = new DBPojo();
		db.setDbtype("mysql");
		db.setInstance("stocks");
//		db.setIp("172.16.50.80");
		db.setIp("localhost");
		db.setPort("3306");
		db.setTableName("600000SS");
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

		// ResultSet querySQL = dbManager.QuerySQL("select * from " + tableName
		// + " limit 1");
		ResultSet querySQL = dbManager.QuerySQL("select count(*) from "
				+ srctableName);
		List<List<String>> rows = DataETLUtil.getRows(querySQL);
		int count = Integer.parseInt(rows.get(0).get(0));

		// for (int j = 0; j < count - window; j++) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into 600000SS11(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,target)  values ");

		for (int j = 0, len = count - window; j < len; j++) {
			String sql = "select close from " + srctableName + " limit " + j
					+ "," + (window + 1);
			System.out.println("sql : " + sql);
			ResultSet rs = dbManager.QuerySQL(sql);
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

	public static void save() {

	}

	public static void load() {

	}

	public static void main(String[] args) {

		clean("600000SS", "600000SS11", 10);
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
