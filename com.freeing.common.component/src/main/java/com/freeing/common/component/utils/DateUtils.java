package com.freeing.common.component.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * 日期工具类
 */
public class DateUtils {
    public final static String DEFAULT_YEAR_FORMAT = "yyyy";
    public final static String DEFAULT_MONTH_FORMAT = "yyyy-MM";
    public final static String DEFAULT_MONTH_FORMAT_SLASH = "yyyy/MM";
    public final static String DEFAULT_MONTH_FORMAT_EN = "yyyy年MM月";
    public final static String DEFAULT_WEEK_FORMAT = "yyyy-ww";
    public final static String DEFAULT_WEEK_FORMAT_EN = "yyyy年ww周";
    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public final static String DEFAULT_DATE_FORMAT_EN = "yyyy年MM月dd日";
    public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public final static String DAY = "DAY";
    public final static String MONTH = "MONTH";
    public final static String WEEK = "WEEK";

    /**
     * 一个月平均天数
     */
    public final static long MAX_MONTH_DAY = 30;

    /**
     * 3个月平均天数
     */
    public final static long MAX_3_MONTH_DAY = 90;

    /**
     * 一年平均天数
     */
    public final static long MAX_YEAR_DAY = 365;


    private DateUtils() {
    }

    /**
     * 格式化日期, 返回格式为 yyyy-MM
     *
     * @param date 日期
     * @return String
     */
    public static String format(LocalDateTime date, String pattern) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        if (pattern == null) {
            pattern = DEFAULT_MONTH_FORMAT;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 根据传入的格式格式化日期.默认格式为MM月dd日
     *
     * @param date 日期
     * @param dataFormat 格式
     * @return String
     */
    public static String format(Date date, String dataFormat) {
        Date dt = date;
        String format = dataFormat;
        if (dt == null) {
            dt = new Date();
        }
        if (format == null) {
            format = DEFAULT_DATE_TIME_FORMAT;
        }
        return new SimpleDateFormat(format).format(dt);
    }

    /**
     * 格式化日期,返回格式为 yyyy-MM-dd
     *
     * @param date 日期
     * @return String
     */
    public static String formatAsDate(LocalDateTime date) {
        return format(date, DEFAULT_DATE_FORMAT);
    }

    public static String formatAsDateEn(LocalDateTime date) {
        return format(date, DEFAULT_DATE_FORMAT_EN);
    }


    public static String formatAsYearMonth(LocalDateTime date) {
        return format(date, DEFAULT_MONTH_FORMAT);
    }

    public static String formatAsYearMonthEn(LocalDateTime date) {
        return format(date, DEFAULT_MONTH_FORMAT_EN);
    }

    /**
     * 格式化日期,返回格式为 yyyy-ww
     *
     * @param date 日期
     * @return String
     */
    public static String formatAsYearWeek(LocalDateTime date) {
        return format(date, DEFAULT_WEEK_FORMAT);
    }

    public static String formatAsYearWeekEn(LocalDateTime date) {
        return format(date, DEFAULT_WEEK_FORMAT_EN);
    }

    /**
     * 格式化日期,返回格式为 yyyy-MM
     *
     * @param date 日期
     * @return String
     */
    public static String formatAsYearMonth(Date date) {
        return new SimpleDateFormat(DEFAULT_MONTH_FORMAT).format(date);
    }

    /**
     * 格式化日期,返回格式为 yyyy-ww
     *
     * @param date 日期
     * @return String
     */
    public static String formatAsYearWeek(Date date) {
        return new SimpleDateFormat(DEFAULT_WEEK_FORMAT).format(date);
    }

    /**
     * 格式化日期,返回格式为 HH:mm:ss 例:12:24:24
     *
     * @param date 日期
     * @return String
     */
    public static String formatAsTime(Date date) {
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(date);
    }

    /**
     * 格式化日期,返回格式为 yyyy-MM-dd
     *
     * @param date 日期
     * @return String
     */
    public static String formatAsDate(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
    }

    /**
     * 格式化日期,返回格式为 yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期
     * @return String
     */
    public static String formatAsDateTime(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(date);
    }

    /**
     * 格式化日期,返回格式为 dd ,即对应的天数.
     *
     * @param date 日期
     * @return String
     */
    public static String formatAsDay(Date date) {
        return new SimpleDateFormat("dd").format(date);
    }

    /**
     * 将字符转换成日期
     *
     * @param dateStr 日期
     * @param format 格式
     * @return Date
     */
    public static Date parse(String dateStr, String format) {
        Date date = null;
        SimpleDateFormat sdateFormat = new SimpleDateFormat(format);
        sdateFormat.setLenient(false);
        try {
            date = sdateFormat.parse(dateStr);
        } catch (Exception ignored) {

        }
        return date;
    }

    /**
     * 根据传入的String返回对应的date
     *
     * @param dateStr 日期
     * @return Date
     */
    public static Date parseAsDate(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 按给定参数返回Date对象
     *
     * @param dateTime 时间对象格式为("yyyy-MM-dd HH:mm:ss");
     * @return Date
     */
    public static Date parseAsDateTime(String dateTime) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        try {
            return simpledateformat.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime LocalDateTime
     * @return Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        if (date == null) {
            return LocalDateTime.now();
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 日期转 LocalDate
     *
     * @param date Date
     * @return LocalDate
     */
    public static LocalDate date2LocalDate(Date date) {
        if (date == null) {
            return LocalDate.now();
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 日期转 LocalTime
     *
     * @param date Date
     * @return LocalTime
     */
    public static LocalTime date2LocalTime(Date date) {
        if (date == null) {
            return LocalTime.now();
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalTime();
    }

    //-计算日期 start


    /**
     * 计算结束时间与当前时间中的天数
     *
     * @param endDate 结束日期
     * @return long 天数
     */
    public static long until(Date endDate) {
        return LocalDateTime.now().until(date2LocalDateTime(endDate), ChronoUnit.DAYS);
    }

    /**
     * 计算结束时间与开始时间中的天数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return long 天数
     */
    public static long until(Date startDate, Date endDate) {
        return date2LocalDateTime(startDate).until(date2LocalDateTime(endDate), ChronoUnit.DAYS);
    }


    /**
     * 计算结束时间与开始时间中的天数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return long 天数
     */
    public static long until(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    public static long until(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    /**
     * 计算2个日期之间的所有的日期 yyyy-MM-dd
     * 含头含尾
     *
     * @param start yyyy-MM-dd
     * @param end   yyyy-MM-dd
     * @return List<String>
     */
    public static List<String> getBetweenDay(Date start, Date end) {
        return getBetweenDay(date2LocalDate(start), date2LocalDate(end));
    }

    /**
     * 计算2个日期之间的所有的日期 yyyy-MM-dd
     * 含头含尾
     *
     * @param start yyyy-MM-dd
     * @param end   yyyy-MM-dd
     * @return List<String>
     */
    public static List<String> getBetweenDay(String start, String end) {
        return getBetweenDay(LocalDate.parse(start), LocalDate.parse(end));
    }

    /**
     * 计算2个日期之间的所有的日期 yyyy-MM-dd
     * 含头含尾
     *
     * @param startDate yyyy-MM-dd
     * @param endDate   yyyy-MM-dd
     * @return List<String>
     */
    public static List<String> getBetweenDay(LocalDate startDate, LocalDate endDate) {
        return getBetweenDay(startDate, endDate, DEFAULT_DATE_FORMAT);
    }

    public static List<String> getBetweenDayEn(LocalDate startDate, LocalDate endDate) {
        return getBetweenDay(startDate, endDate, DEFAULT_DATE_FORMAT_EN);
    }

    public static List<String> getBetweenDay(LocalDate startDate, LocalDate endDate, String pattern) {
        if (pattern == null) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        List<String> list = new ArrayList<>();
        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        String finalPattern = pattern;
        Stream.iterate(startDate, d -> d.plusDays(1)).
                limit(distance + 1)
                .forEach(f -> list.add(f.format(DateTimeFormatter.ofPattern(finalPattern))));
        return list;
    }


    /**
     * 计算2个日期之间的所有的周 yyyy-ww
     * 含头含尾
     *
     * @param start yyyy-MM-dd
     * @param end   yyyy-MM-dd
     * @return List<String>
     */
    public static List<String> getBetweenWeek(Date start, Date end) {
        return getBetweenWeek(date2LocalDate(start), date2LocalDate(end));
    }

    /**
     * 计算2个日期之间的所有的周 yyyy-ww
     * 含头含尾
     *
     * @param start yyyy-MM-dd
     * @param end   yyyy-MM-dd
     * @return List<String>
     */
    public static List<String> getBetweenWeek(String start, String end) {
        return getBetweenWeek(LocalDate.parse(start), LocalDate.parse(end));
    }

    /**
     * 计算2个日期之间的所有的周 yyyy-ww
     * 含头含尾
     *
     * @param startDate yyyy-MM-dd
     * @param endDate   yyyy-MM-dd
     * @return List<String>
     */
    public static List<String> getBetweenWeek(LocalDate startDate, LocalDate endDate) {
        return getBetweenWeek(startDate, endDate, DEFAULT_WEEK_FORMAT);
    }

    public static List<String> getBetweenWeek(LocalDate startDate, LocalDate endDate, String pattern) {
        List<String> list = new ArrayList<>();

        long distance = ChronoUnit.WEEKS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, d -> d.plusWeeks(1)).
                limit(distance + 1).forEach(f -> list.add(f.format(DateTimeFormatter.ofPattern(pattern))));
        return list;
    }

    /**
     * 计算2个日期之间的所有的月 yyyy-MM
     *
     * @param start yyyy-MM-dd
     * @param end   yyyy-MM-dd
     * @return List<String>
     */
    public static List<String> getBetweenMonth(Date start, Date end) {
        return getBetweenMonth(date2LocalDate(start), date2LocalDate(end));
    }

    /**
     * 计算2个日期之间的所有的月 yyyy-MM
     *
     * @param start yyyy-MM-dd
     * @param end   yyyy-MM-dd
     * @return List<String>
     */
    public static List<String> getBetweenMonth(String start, String end) {
        return getBetweenMonth(LocalDate.parse(start), LocalDate.parse(end));
    }

    /**
     * 计算2个日期之间的所有的月 yyyy-MM
     *
     * @param startDate yyyy-MM-dd
     * @param endDate   yyyy-MM-dd
     * @return List<String>
     */
    public static List<String> getBetweenMonth(LocalDate startDate, LocalDate endDate) {
        return getBetweenMonth(startDate, endDate, DEFAULT_MONTH_FORMAT);
    }

    public static List<String> getBetweenMonth(LocalDate startDate, LocalDate endDate, String pattern) {
        List<String> list = new ArrayList<>();
        long distance = ChronoUnit.MONTHS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }

        Stream.iterate(startDate, d -> d.plusMonths(1))
                .limit(distance + 1)
                .forEach(f -> list.add(f.format(DateTimeFormatter.ofPattern(pattern))));
        return list;
    }
}
