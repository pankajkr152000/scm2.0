package com.scm.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.scm.contants.SCMConstants;

public class SCMDate {

    public static Date getBusinessDate() {

        TimeZone tz = TimeZone.getTimeZone(SCMConstants.LOCALE_TIMEZONE);
        Calendar calender = new GregorianCalendar(tz);
        return calender.getTime();
    }

}
