package com.scm.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class SCMDate {

    public static Date getBusinessDate() {
        Locale indiaTimeZone = Locale.of("en", "IN");
        TimeZone tz = TimeZone.getTimeZone(indiaTimeZone.toString());
        Calendar calender = new GregorianCalendar(tz);
        return calender.getTime();
    }

}
