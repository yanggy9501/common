package com.freeing.common.component.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class DateUtilsTest {

    @Test
    public void testQuarter1() {
        assertEquals(1, DateUtils.quarter(LocalDate.of(2020, 1, 1)));
    }

    @Test
    public void testQuarter2() {
        assertEquals(2, DateUtils.quarter(new GregorianCalendar(2020, Calendar.JUNE, 5).getTime()));
    }

    @Test
    public void testLocalDateTime2Date() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            DateUtils.localDateTime2Date(LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
    }

    @Test
    public void testDate2LocalDateTime() {
        assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
            DateUtils.date2LocalDateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testDate2LocalDate() {
        assertEquals(LocalDate.of(2020, 1, 1),
            DateUtils.date2LocalDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testDate2LocalTime() {
        assertEquals(LocalTime.of(12, 0, 0),
            DateUtils.date2LocalTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testUntil1() {
        assertEquals(2L, DateUtils.until(new GregorianCalendar(2023, Calendar.MAY, 1).getTime()));
    }

    @Test
    public void testUntil2() {
        assertEquals(-29L, DateUtils.until(new GregorianCalendar(2023, Calendar.APRIL, 30).getTime(),
            new GregorianCalendar(2023, Calendar.APRIL, 1).getTime()));
    }

    @Test
    public void testUntil3() {
        assertEquals(0L, DateUtils.until(LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
    }

    @Test
    public void testUntil4() {
        assertEquals(0L, DateUtils.until(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)));
    }

    @Test
    public void testGetBetweenDay1() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenDay(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime()));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenDay(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testGetBetweenDay2() {
        assertEquals(Arrays.asList("value"), DateUtils.getBetweenDay("start", "end"));
        assertEquals(Collections.emptyList(), DateUtils.getBetweenDay("start", "end"));
    }

    @Test
    public void testGetBetweenDay3() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenDay(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenDay(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)));
    }

    @Test
    public void testGetBetweenDayEn() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenDayEn(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenDayEn(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)));
    }

    @Test
    public void testGetBetweenDay4() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenDay(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), "pattern"));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenDay(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), "pattern"));
    }

    @Test
    public void testGetBetweenWeek1() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenWeek(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenWeek(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testGetBetweenWeek2() {
        assertEquals(Arrays.asList("value"), DateUtils.getBetweenWeek("start", "end"));
        assertEquals(Collections.emptyList(), DateUtils.getBetweenWeek("start", "end"));
    }

    @Test
    public void testGetBetweenWeek3() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenWeek(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenWeek(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)));
    }

    @Test
    public void testGetBetweenWeek4() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenWeek(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), "pattern"));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenWeek(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), "pattern"));
    }

    @Test
    public void testGetBetweenMonth1() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenMonth(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenMonth(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testGetBetweenMonth2() {
        assertEquals(Arrays.asList("value"), DateUtils.getBetweenMonth("start", "end"));
        assertEquals(Collections.emptyList(), DateUtils.getBetweenMonth("start", "end"));
    }

    @Test
    public void testGetBetweenMonth3() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenMonth(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenMonth(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)));
    }

    @Test
    public void testGetBetweenMonth4() {
        assertEquals(Arrays.asList("value"),
            DateUtils.getBetweenMonth(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), "pattern"));
        assertEquals(Collections.emptyList(),
            DateUtils.getBetweenMonth(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), "pattern"));
    }

    @Test
    public void testGetTodayBegin() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getTodayBegin());
    }

    @Test
    public void testGetTodayEnd() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getTodayEnd());
    }

    @Test
    public void testGetYesterdayBegin() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getYesterdayBegin());
    }

    @Test
    public void testGetYesterDayEnd() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getYesterDayEnd());
    }

    @Test
    public void testGetTomorrowBegin() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getTomorrowBegin());
    }

    @Test
    public void testGetTomorrowEnd() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getTomorrowEnd());
    }

    @Test
    public void testGetBeginOfWeek() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getBeginOfWeek());
    }

    @Test
    public void testGetEndOfWeek() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getEndOfWeek());
    }

    @Test
    public void testGetBeginOfMonth() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getBeginOfMonth());
    }

    @Test
    public void testGetEndOfMonth() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getEndOfMonth());
    }

    @Test
    public void testGetBeginOfYear() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getBeginOfYear());
    }

    @Test
    public void testGetEndOfYear() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), DateUtils.getEndOfYear());
    }

    @Test
    public void testGetBeginOf() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            DateUtils.getBeginOf(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testGetEndOf() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            DateUtils.getEndOf(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testGetNowYear() {
        assertEquals(0, DateUtils.getNowYear());
    }

    @Test
    public void testGetNowMonth() {
        assertEquals(0, DateUtils.getNowMonth());
    }

    @Test
    public void testNextDay() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            DateUtils.nextDay(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), 0));
    }

    @Test
    public void testFrontDay() {
        assertEquals(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            DateUtils.frontDay(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), 0));
    }
}
