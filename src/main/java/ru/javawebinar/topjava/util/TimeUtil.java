package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class TimeUtil {

    public static final LocalDateTime NOW_WITHOUT_SECOND_NANO = LocalDateTime.now().withSecond(0).withNano(0);

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String localTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }

    public static LocalDateTime stringToLocalDateTime(String localDateTime) {
        return LocalDateTime.parse(localDateTime);
    }
}