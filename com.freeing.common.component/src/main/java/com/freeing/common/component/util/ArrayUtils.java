package com.freeing.common.component.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数组工具类
 *
 * @author yanggy
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {
    /**
     * 数组转换为集合
     *
     * @param args 可变参数
     * @return List
     */
    public static <T> List<T> toList(T... args) {
        return Arrays.stream(args).collect(Collectors.toList());
    }

    /**
     * 将多个参数封装成数组
     *
     * @param args 可变参数
     * @return 数组
     */
    public static <T> T[] toArray(T... args) {
        return args;
    }
}
