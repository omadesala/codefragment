package com.omade.stock;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.elasticsearch.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
        Date parseDate = Utils.parseStr2Date(dateStr);
        System.out.println("date is: " + parseDate.toString());
    }

    @Test
    public void testDateFormat() {

        String dateStr = "2009-06-08";

        FastDateFormat fdf = DateFormatUtils.ISO_DATE_FORMAT;
        String pattern = fdf.getPattern();

        System.out.println("date pattern is: " + pattern);
    }

    @Test
    public void testDate2String() {

        String dateStr = null;
        Date date = new Date();
        String parseDate2Str = Utils.parseDate2Str(date);
        System.out.println("date string is: " + parseDate2Str);
    }

    @Test
    public void testTransfrom() {

        String dateStr = "2009-06-08";
        FastDateFormat fdf = DateFormatUtils.ISO_DATE_FORMAT;
        String pattern = fdf.getPattern();
        try {
            Date parseDate = DateUtils.parseDate(dateStr, new String[] { new String(pattern) });
            System.out.println("date pattern is: " + parseDate.toLocaleString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    @Ignore
    public void testPrintCollection() {

        List<StockData> lists = Lists.newArrayList();

        lists.add(new StockData());
        lists.add(new StockData());
        lists.add(new StockData());

        Utils.printInfo(lists);

    }
}
