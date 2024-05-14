package com.freeing.common.support.poi.excle.convertor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Date2StringConvertor implements Convertor<Date, String> {

    private final String pattern;

    public Date2StringConvertor(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String convert(Date from) {
        if (from == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern).format(from);
        } catch (Exception ignored) {
            return "Wrong pattern";
        }
    }
}
