package com.chinacloud.blackhole.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlManager {

    // MySqlManager
    public static final String MYSQL = "mysql";

    // private static Log log = LogFactory.getLog(MySqlManager.class);

    private DBPojo db;
    private Connection conn = null;
    private PreparedStatement statement = null;

    public MySqlManager(DBPojo db) {
        this.db = db;
    }

    public void Connection() throws SQLException, ClassNotFoundException {

        ConnectionFactory cf = null;

        switch (this.db.getDbtype()) {
        case MYSQL:
            cf = new MySqlConnectionFactory();
            break;
        default:
            break;
        }

        this.conn = cf.getDBConnection(this.db);

    }

    public ResultSet QuerySQL(String sql) {
        ResultSet rs = null;
        try {
            this.statement = this.conn.prepareStatement(sql);
            rs = this.statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public boolean executeSQL(String sql) {
        try {

            this.statement = this.conn.prepareStatement(sql);
            this.statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // log.info("-- Error: Insert database failed!");
            e.printStackTrace();
            System.out.println("executeSQL exception: " + e.getMessage());
        }

        return false;
    }

    public void closeStatement() {
        try {
            if (this.statement != null) {
                this.statement.close();
            }
        } catch (Exception e) {
            // log.info("-- Error: close statement failed!");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (Exception e) {
            // log.info("-- Error: Deconnect database failed!");
            e.printStackTrace();
        }
    }

}
