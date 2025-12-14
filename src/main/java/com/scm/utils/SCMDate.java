package com.scm.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scm.constants.SCMConstants;
import com.scm.constants.SCMConstants.PeriodType;

public class SCMDate {

    public static Date getBusinessDate() {
        Locale indiaTimeZone = Locale.of("en", "IN");
        TimeZone tz = TimeZone.getTimeZone(indiaTimeZone.toString());
        Calendar calender = new GregorianCalendar(tz);
        return calender.getTime();
    }

    public static String getYear() {
        TimeZone tz = TimeZone.getTimeZone(SCMConstants.LOCALE_TIMEZONE);
        Calendar calendar = Calendar.getInstance(tz);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }


    private static final Logger log = LoggerFactory.getLogger(SCMDate.class);
    private static final ZoneId ZONE = ZoneId.of(SCMConstants.LOCALE_TIMEZONE);

    public static Date addDate(Date currDate, int period, PeriodType periodType) {

        // 🔹 Null checks
        if (currDate == null) {
            log.error("addDate failed: currDate is null");
            throw new IllegalArgumentException("currDate must not be null");
        }

        if (periodType == null) {
            log.error("addDate failed: periodType is null");
            throw new IllegalArgumentException("periodType must not be null");
        }

        log.debug("addDate called with currDate={}, period={}, periodType={}",
                currDate, period, periodType);

        LocalDate localDate = currDate.toInstant()
                .atZone(ZONE)
                .toLocalDate();

        switch (periodType) {
            case DAY -> localDate = localDate.plusDays(period);
            case WEEK -> localDate = localDate.plusWeeks(period);
            case MONTH -> localDate = localDate.plusMonths(period);
            case YEAR -> localDate = localDate.plusYears(period);
            default -> {
                log.error("Invalid periodType: {}", periodType);
                throw new IllegalArgumentException("Unsupported periodType");
            }
        }

        Date resultDate = Date.from(localDate.atStartOfDay(ZONE).toInstant());

        log.info("addDate result: {}", resultDate);
        return resultDate;
    }

    public static Date subtractDate(Date currDate, int period, PeriodType periodType) {
        log.debug("subtractDate called");
        return addDate(currDate, -period, periodType);
    }


}
