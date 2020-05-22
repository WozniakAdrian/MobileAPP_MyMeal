package com.AdrianWozniak.mymeal.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceDateTime {

    /**
     * Return current day of week in english
     * @return string day of week
     */
    public static String getDayOfWeekName(){

        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
        return simpleDateformat.format(new Date());
    }

    /**
     * Return current number of day and english name
     * @return
     */
    public static String getNumberOfDayAndName(){
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd EEEE", new Locale("en", "GB"));
        System.out.println(simpleDateformat.format(new Date()));
        return simpleDateformat.format(new Date());
    }


}
