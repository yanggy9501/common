package com.freeing.common.support.pipeline.util;

public class StringUtil {

    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    public static String simpleClassName(Object o) {
        if (o == null) {
            return "null_object";
        } else {
            return simpleClassName(o.getClass());
        }
    }


    public static String simpleClassName(Class<?> handlerType) {
        String className = handlerType.getName();
        final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if (lastDotIdx > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }
}
