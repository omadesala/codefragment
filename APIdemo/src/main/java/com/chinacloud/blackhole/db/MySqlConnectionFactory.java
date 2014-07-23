package com.chinacloud.blackhole.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionFactory implements ConnectionFactory {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    @Override
    public Connection getDBConnection(DBPojo db) throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        String jdbc_url = getJdbcUrl(db);
        return DriverManager.getConnection(jdbc_url, db.getUser(), db.getPwd());
    }

    @Override
    public String getJdbcUrl(DBPojo db) {

        String charset = "?characterEncoding=UTF-8";
        String jdbc_url = "jdbc:mysql://" + db.getIp() + ":" + db.getPort() + "/" + db.getInstance() + charset;

        return jdbc_url;
    }
}
