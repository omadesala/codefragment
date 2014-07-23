package com.omade.stock;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testparseDate() {

        String dateStr = "2009-06-08";
        Date parseDate = Utils.parseDate(dateStr);
        System.out.println("date is: " + parseDate.toString());
    }
}
