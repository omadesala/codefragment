package com.omade.stock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Date parseStr2Date(String dateStr) {

        // DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format1 = sdf;
        Date date = null;
        try {
            date = sdf.parse(dateStr);
            System.out.println(date.toLocaleString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return date;
    }

    public static String parseDate2Str(Date date) {
        //
        // DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        // DateFormat format3 = new SimpleDateFormat("h:mm a");
        // DateFormat format4 = new
        // SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        // DateFormat format5 = new SimpleDateFormat("EEE, MMM, dd, ''yyyy");
        // DateFormat format6 = new
        // SimpleDateFormat("yyyy.MM.dd kk:mm 'o''clock' a, zzzz");
        // DateFormat format7 = new
        // SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date);

        return dateStr;
    }

}
