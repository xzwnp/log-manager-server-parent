package com.example.xiao.logmanager.server.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    /**
     * 13位时间戳转LocalDatetime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime of(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(long timestamp, String pattern) {
        LocalDateTime dateTime = of(timestamp);
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
