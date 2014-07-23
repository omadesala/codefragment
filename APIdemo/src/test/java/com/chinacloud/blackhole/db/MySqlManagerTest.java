package com.chinacloud.blackhole.db;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MySqlManagerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSql() {

        DBPojo db = new DBPojo();
        db.setDbtype("mysql");
        db.setInstance("stocks");
        db.setIp("localhost");
        db.setPort("3306");
        db.setName("test");
        db.setPwd("123456");

        db.setUser("root");

        MySqlManager mySqlManager = new MySqlManager(db);
    }

    @Test
    @Ignore
    public void test() {
        fail("Not yet implemented");
    }

}
