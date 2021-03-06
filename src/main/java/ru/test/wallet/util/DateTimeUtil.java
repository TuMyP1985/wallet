package ru.test.wallet.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static  <T extends Comparable<? super T>> boolean isBetweenHalfOpen(T lt, T start, T end) {

        return (start==null ||  lt.compareTo(start) >= 0) &&
                (end==null || lt.getClass().equals(LocalDate.class)&&lt.compareTo(end) <= 0 || lt.compareTo(end) < 0 );
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

