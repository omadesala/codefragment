package com.omade.utils;

import weka.core.Instances;
import weka.experiment.InstanceQuery;

/**
 * Created by ping on 16-4-12.
 */
public class WekaMysqlUtil {

    public static Instances load() {

        InstanceQuery query = null;
        try {
            query = new InstanceQuery();
            query.setUsername("root");
            query.setPassword("root");
//			query.setPassword("hadoop");
            query.setQuery("select * from 600000SS11");
            query.setDatabaseURL("jdbc:mysql://localhost:3306/stocks?characterEncoding=UTF8");
//			query.setDatabaseURL("jdbc:mysql://172.16.50.80:3306/stocks?characterEncoding=UTF8");
            Instances data = query.retrieveInstances();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if your data is sparse, then you can say so too
        // query.setSparseData(true);
        throw new IllegalStateException("error");
    }

    public static void main(String[] args) {

        Instances load = load();
        System.out.println("num: " + load.size());
    }
}
