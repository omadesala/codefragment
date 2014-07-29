package com.omade.stock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.FastDateFormat;

public class Utils {

    public static Date parseStr2Date(String dateStr) {

        FastDateFormat fdf = DateFormatUtils.ISO_DATE_FORMAT;
        SimpleDateFormat sdf = new SimpleDateFormat(fdf.getPattern());
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return date;
    }

    public static String parseDate2Str(Date date) {

        FastDateFormat fdf = DateFormatUtils.ISO_DATE_FORMAT;
        DateFormat format = new SimpleDateFormat(fdf.getPattern());
        String dateStr = format.format(date);

        return dateStr;
    }

    public static void printInfo(Collection<?> collection) {

        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }

    }

}
