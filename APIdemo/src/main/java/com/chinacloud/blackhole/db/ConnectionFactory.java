package com.chinacloud.blackhole.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

    public Connection getDBConnection(DBPojo db) throws ClassNotFoundException, SQLException;

    public String getJdbcUrl(DBPojo db);

}
