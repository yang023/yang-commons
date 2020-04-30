package cn.yang.commons;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * @author yang
 *
 * 统一时间生成，带时区，提供相关时间格式转换如：时间戳、格式化、转Date
 */
public class TimeUtil {
    /**
     * 生成10位时间戳
     *
     * @param time 生成时间戳的具体时间，null则为当前时间
     * @return 时间戳
     */
    public static Long timestamp(LocalDateTime time) {
        if (Objects.isNull(time)) {
            time = LocalDateTime.now();
        }
        return time.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public static LocalDateTime max() {
        return LocalDateTime.of(9999, 12, 31, 23, 59, 59);
    }

    public static Long timestamp() {
        return timestamp(null);
    }

    public static LocalDateTime fromTimestamp(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, OffsetDateTime.now().getOffset());
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }

    public static String format(LocalDateTime time, String formatter) {
        return time.format(DateTimeFormatter.ofPattern(formatter));
    }

    public static String format(String formatter) {
        return format(LocalDateTime.now(), formatter);
    }

    public static String format() {
        return format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
    }

    public static Date toDate(LocalDateTime dateTime) {
        Instant instant = Instant.ofEpochSecond(timestamp(dateTime));
        return Date.from(instant);
    }

    public static LocalDateTime fromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime firstDayOfMonth(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static LocalDateTime firstDayOfNextMonth(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.with(TemporalAdjusters.firstDayOfNextMonth()).toLocalDate();
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static LocalDateTime firstDayOfYear(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.with(TemporalAdjusters.firstDayOfYear()).toLocalDate();
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static LocalDateTime firstDayOfNextYear(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.with(TemporalAdjusters.firstDayOfNextYear()).toLocalDate();
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static LocalDateTime lastDayOfMonth(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static LocalDateTime firstDayOfMonth() {
        return firstDayOfMonth(now());
    }

    public static LocalDateTime firstDayOfNextMonth() {
        return firstDayOfNextMonth(now());
    }

    public static LocalDateTime firstDayOfYear() {
        return firstDayOfYear(now());
    }

    public static LocalDateTime firstDayOfNextYear() {
        return firstDayOfNextYear(now());
    }

    public static LocalDateTime lastDayOfMonth() {
        return lastDayOfMonth(now());
    }

}
