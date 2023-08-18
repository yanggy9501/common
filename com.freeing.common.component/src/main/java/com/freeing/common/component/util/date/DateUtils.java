package com.freeing.common.component.util.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

/**
 * 日期日历工具类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
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
     * 获取 LocalDate 属于第几季度
     *
     * @param localDate LocalDate
     * @return
     */
    public static int quarter(LocalDate localDate) {
        int monthValue = localDate.getMonthValue();
        return (monthValue >> 2) + 1;
    }

    /**
     * 获取 Date 属于第几季度
     *
     * @param date Date
     * @return
     */
    public static int quarter(Date date) {
        LocalDate localDate = date2LocalDate(date);
        return quarter(localDate);
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

    /**
     * 计算结束时间与当前时间中的天数
     *
     * @param endDate 结束日期
     * @return long 天数
     */
    public static long until(Date endDate) {
        return LocalDate.now().until(date2LocalDate(endDate), ChronoUnit.DAYS);
    }

    /**
     * 计算结束时间与开始时间中的天数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return long 天数
     */
    public static long until(Date startDate, Date endDate) {
        return date2LocalDate(startDate).until(date2LocalDate(endDate), ChronoUnit.DAYS);
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

    /**
     * 获取当天的开始时间
     *
     * @return Date
     */
    public static Date getTodayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当天的结束时间
     *
     * @return Date
     */
    public static Date getTodayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取昨天的开始时间
     *
     * @return Date
     */
    public static Date getYesterdayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getTodayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取昨天的结束时间
     *
     * @return Date
     */
    public static Date getYesterDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getTodayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取明天的开始时间
     *
     * @return Date
     */
    public static Date getTomorrowBegin() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getTodayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取明天的结束时间
     *
     * @return Date
     */
    public static Date getTomorrowEnd() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getTodayEnd());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取本周的开始时间
     *
     * @return Date
     */
    public static Date getBeginOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        // 周日
        if (dayofweek == 1) {
            dayofweek += 7;
        } else {
            // 其他 -1
            dayofweek--;
        }
        // + 1 是这天也算，注入周6是 -6 + 1 才是周一
        cal.add(Calendar.DATE, -dayofweek + 1);
        cal.set(cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH),
            0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取本周的结束时间
     *
     * @return Date
     */
    public static Date getEndOfWeek() {
        Calendar cal = Calendar.getInstance();
        // 周一时间
        cal.setTime(getBeginOfWeek());
        // +6 天
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.getTime();
    }

    /**
     * 获取本月的开始时间
     *
     * @return Date
     */
    public static Date getBeginOfMonth() {
        Calendar calendar = Calendar.getInstance();
        // 某年某月一日
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getBeginOf(calendar.getTime());
    }

    /**
     * 获取本月的结束时间
     *
     * @return Date
     */
    public static Date getEndOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        // 计算一个月有几天
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getEndOf(calendar.getTime());
    }

    /**
     * 获取本年的开始时间
     *
     * @return Date
     */
    public static Date getBeginOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return getBeginOf(cal.getTime());
    }

    /**
     * 获取本年的结束时间
     *
     * @return Date
     */
    public static Date getEndOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getEndOf(cal.getTime());
    }

    /**
     * 获取某个日期的开始时间
     *
     * @param date  Date
     * @return Date
     */
    public static Date getBeginOf(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.set(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某个日期的结束时间
     *
     * @param date Date
     * @return Date
     */
    public static Date getEndOf(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.set(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取今年是哪一年
     *
     * @return Integer
     */
    public static int getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(Calendar.YEAR);
    }

    /**
     * 获取本月是哪一月
     *
     * @return 月份
     */
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        // 月份（0-11） 0 代表1月
        return gc.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回某个日期，后 next 几天的日期时间
     *
     * @param date Date
     * @param step 步长
     * @return Date
     */
    public static Date nextDay(Date date, int step) {
        addDays(date, step);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + step);
        return cal.getTime();
    }

    /**
     * 返回某个日期的前几天的日期时间
     *
     * @param date Date
     * @param step 步长
     * @return Date
     */
    public static Date frontDay(Date date, int step) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - step);
        return cal.getTime();
    }
}
