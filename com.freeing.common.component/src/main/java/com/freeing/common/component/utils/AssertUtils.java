package com.freeing.common.component.utils;

/**
 * 断言工具
 *
 * @author yanggy
 */
public class AssertUtils {
    public static <T> void checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
    }
}

