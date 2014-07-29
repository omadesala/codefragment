package com.omade.stock;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.chinacloud.blackhole.db.MySqlManager;

public class StorageTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Ignore
    public void testGetLast() {

        MySqlManager mysqlmanager = Mockito.mock(MySqlManager.class);

        ResultSet value = Mockito.mock(ResultSet.class);

        // Mockito.when(value.getDate("date")).thenReturn("");

        Mockito.when(mysqlmanager.QuerySQL("select * from vips order by date limit 1")).thenReturn(value);

        Storage storage = new Storage(mysqlmanager);

        storage.getLastRecordByColumn("vips");
    }

    @Test
    public void testGetByDateLength() {

    }

}
