package com.omade.stock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Date parseDate(String dateStr) {

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

}
