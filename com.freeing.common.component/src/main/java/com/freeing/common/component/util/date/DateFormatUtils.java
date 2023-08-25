package com.freeing.common.component.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期日历工具类
 */
public class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {

    public final static String FORMAT_yyyy = "yyyy";
    public final static String FORMAT_MM = "MM";
    public final static String FORMAT_dd = "dd";
    public final static String FORMAT_yyyyMMdd = "yyyy-MM-dd";
    public final static String FORMAT_yyyyMMdd_CH = "yyyy年MM月dd日";
    public final static String FORMAT_HHmmss = "HH:mm:ss";
    public final static String FORMAT_HHmm = "HH:mm";
    public final static String FORMAT_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public final static String FORMAT_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";

    public final static String FORMAT_COMPACT_yyyyMMdd = "yyyyMMdd";
    public final static String FORMAT_COMPACT_yyyyMM = "yyyyMM";
    public final static String FORMAT_COMPACT_HHmmss = "HHmmss";
    public final static String FORMAT_COMPACT_HHmm = "HHmm";
    public final static String FORMAT_COMPACT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public final static String FORMAT_COMPACT_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";


    public final static String DEFAULT_MONTH_FORMAT = "yyyy-MM";
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


    private DateFormatUtils() {
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
}
