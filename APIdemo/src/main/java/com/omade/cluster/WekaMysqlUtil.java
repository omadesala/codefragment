package com.omade.cluster;

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
            query.setQuery("select * from 600000SS");
            query.setDatabaseURL("jdbc:mysql://localhost:3306/stocks?characterEncoding=UTF8");
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
