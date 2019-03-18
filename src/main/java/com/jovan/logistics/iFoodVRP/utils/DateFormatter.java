package com.jovan.logistics.iFoodVRP.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-16 18:57
 */
@Slf4j
public class DateFormatter {

    private enum DATE_PATTERN {
        defaultFromat,
        isoFormat
    }

    public static final String APPLICATION_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final static SimpleDateFormat defaultFormatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);

    private final static SimpleDateFormat isoFormatter = new SimpleDateFormat(APPLICATION_DATE_TIME_FORMAT);


    public static Optional<String> formatDate(Calendar calendar) {
        return formatDate(calendar.getTime(), DATE_PATTERN.isoFormat);
    }

    public static Optional<String> formatDateDefaultPattern(Date date) {
        return formatDate(date, DATE_PATTERN.defaultFromat);
    }

    public static Optional<String> formatCalendarDefaultPattern(Calendar calendar) {
        return formatDate(calendar.getTime(), DATE_PATTERN.defaultFromat);
    }

    public static Optional<Date> formatDateStr(String str) {
        return formatDateStr(str, DATE_PATTERN.isoFormat);
    }

    public static Optional<Date> formatDateToStrDefaultPattern(String str) {
        return formatDateStr(str, DATE_PATTERN.defaultFromat);
    }


    private static Optional<Date> formatDateStr(String str, DATE_PATTERN date_pattern) {
        try {
            return Optional.of(DATE_PATTERN.defaultFromat.equals(date_pattern) ?
                    defaultFormatter.parse(str) : isoFormatter.parse(str));
        } catch (Exception ex) {
            log.error("Não foi possível converter a data para String", ex);
            return Optional.empty();
        }
    }

    private static Optional<String> formatDate(Date date, DATE_PATTERN date_pattern) {
        try {
            return Optional.of(DATE_PATTERN.defaultFromat.equals(date_pattern) ? defaultFormatter.format(date) : isoFormatter.format(date));
        } catch (Exception ex) {
            log.error("Não foi possível converter a data para String", ex);
            return Optional.empty();
        }

    }

}
