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

    public static final String APPLICATION_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private final static SimpleDateFormat defaultFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat isoFormatter = new SimpleDateFormat(APPLICATION_DATE_TIME_FORMAT);

    public static Optional<String> formatDate(Date date) {
        try {
            return Optional.of(isoFormatter.format(date));
        } catch (Exception ex) {
            log.error("Não foi possível converter a data para String", ex);
            return Optional.empty();
        }

    }

    public static Optional<String> formatDate(Calendar calendar) {
        return formatDate(calendar.getTime());
    }

    public static Optional<Date> formatDateStr(String str) {
        try {
            return Optional.of(isoFormatter.parse(str));
        } catch (Exception ex) {
            log.error("Não foi possível converter a data para String", ex);
            return Optional.empty();
        }
    }

    public static Optional<Calendar> formatCalendarStr(String str) {
        Optional<Date> date = formatDateStr(str);
        if (date.isPresent()) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date.get());
            return Optional.of(instance);
        }
        return Optional.empty();
    }

}
