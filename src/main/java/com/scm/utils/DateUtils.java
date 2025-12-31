package com.scm.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scm.constants.SCMConstants;
import com.scm.constants.SCMConstants.PeriodType;

public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
    
    private static final ZoneId ZONE = ZoneId.of(SCMConstants.LOCALE_TIMEZONE);

    private DateUtils() {} // prevent object creation

    /**
     * String → LocalDate
     * Converts String date to LocalDate
     * LocalDate dob = DateUtil.stringToDate("15-08-1999", "dd-MM-yyyy");
     */
    public static LocalDate stringToDate(String dateStr, String format) {
        if (dateStr == null || format == null) {
            log.warn("stringToDate failed: null input");
            return null;
        }

        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(format);

            return LocalDate.parse(dateStr, formatter);

        } catch (DateTimeParseException ex) {
            log.error("Invalid date '{}' for format '{}'", dateStr, format, ex);
            return null;
        }
    }

    /**
     * Converts LocalDate to String
     * LocalDate → String
     * String dobUI = DateUtil.dateToString(dob, "dd-MM-yyyy");
     */
    public static String dateToString(LocalDate date, String format) {
        if (date == null || format == null) {
            log.warn("dateToString failed: null input");
            return null;
        }

        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(format);

            return date.format(formatter);

        } catch (Exception ex) {
            log.error("dateToString failed for date {}", date, ex);
            return null;
        }
    }

    /**
     * Converts date String from one format to another
     * String → String (Format Change)
     * String dbDate = DateUtil.formatDate("15-08-1999", "dd-MM-yyyy", "yyyy-MM-dd");
     */
    public static String formatDate(String dateStr, String inputFormat, String outputFormat) {

        LocalDate date = stringToDate(dateStr, inputFormat);
        if (date == null) {
            return null;
        }
        return dateToString(date, outputFormat);
    }

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
            case DAYS -> localDate = localDate.plusDays(period);
            case WEEKS -> localDate = localDate.plusWeeks(period);
            case MONTHS -> localDate = localDate.plusMonths(period);
            case YEARS -> localDate = localDate.plusYears(period);
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
