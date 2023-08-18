package com.freeing.common.component.util.date;

import com.sun.istack.internal.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 时间间隔
 *
 * @author yanggy
 */
public class DateBetween {
    /**
     * 起始时间
     */
    Date start;

    /**
     * 结束时间
     */
    Date end;

    public DateBetween(@NotNull Date start, @NotNull Date end) {
        this.start = start;
        this.end = end;
    }

    public long between(@NotNull ChronoUnit dateUnit) {
        return 0;
    }



    public static void main(String[] args) throws ParseException {
        String s = "2023-08-10 10:10:10";
        String s2 = "2023-08-13 9:10:10";
        String s3 = "yyyy-MM-dd HH:mm:ss";
        Date d1 = new SimpleDateFormat(s3).parse(s);
        Date d2 = new SimpleDateFormat(s3).parse(s2);

        System.out.println(new DateBetween(d1, d2).between(ChronoUnit.WEEKS));

        Duration between = Duration.between(d1.toInstant(), d2.toInstant());
        System.out.println(between.toDays());
        System.out.println(between.toHours());

    }
}
