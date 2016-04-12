package com.omade.cluster;

import com.chinacloud.blackhole.db.DBPojo;
import com.chinacloud.blackhole.db.MySqlManager;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ping on 16-4-12.
 */
public class WekaMysqlUtil {

    public static Instances load() {

        InstanceQuery query = null;
        try {
            query = new InstanceQuery();
            query.setUsername("root");
            // query.setPassword("root");
            query.setPassword("hadoop");
            query.setQuery("select * from 600000SS");
            // query.setDatabaseURL("jdbc:mysql://localhost:3306/stocks?characterEncoding=UTF8");
            query.setDatabaseURL("jdbc:mysql://172.16.50.80:3306/stocks?characterEncoding=UTF8");
            Instances data = query.retrieveInstances();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if your data is sparse, then you can say so too
        // query.setSparseData(true);
        throw new IllegalStateException("error");
    }

    public static void etl() {

        DBPojo db = new DBPojo();
        db.setDbtype("mysql");
        db.setInstance("stocks");
        db.setIp("localhost");
        db.setPort("3306");
        db.setTableName("600000SS");
        db.setUser("root");
        db.setPwd("root");

        MySqlManager mySqlManager = new MySqlManager(db);

        try {

            mySqlManager.Connection();

            int start = 0;
            int len = 10;

            while (start < (242 - len)) {
                ResultSet resultSet = mySqlManager.QuerySQL("select close from 600000SS limit" + start + "," + len);
                StringBuffer sb = new StringBuffer();
                sb.append("values(");

                while (resultSet.next()) {
                    double aDouble = resultSet.getDouble(1);
                    sb.append(aDouble).append(",");
                }

                mySqlManager.executeSQL("insert into 600000SS11(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,target) values(" + "," + ")");
                start++;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {

        Instances load = load();
        System.out.println("num: " + load.size());
    }
}
