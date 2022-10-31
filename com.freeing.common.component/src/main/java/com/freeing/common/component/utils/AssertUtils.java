package com.freeing.common.component.utils;

/**
 * 断言工具
 *
 * @author yanggy
 */
public class AssertUtils {
    public static void assertEquals(int a, int b, String msg) {
        if (a != b) {
            throwArgException(msg);
        }
    }

    public static void assertEquals(String str1, String str2, String msg) {
        if (!str1.equals(str2)) {
            throwArgException(msg);
        }
    }

    private static void throwArgException(String msg) {
        if (StringUtils.isNotEmpty(msg)) {
            throw  new IllegalArgumentException(msg);
        }
        throw  new IllegalArgumentException(msg);
    }
}
