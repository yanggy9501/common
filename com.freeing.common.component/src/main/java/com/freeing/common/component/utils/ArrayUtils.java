package com.freeing.common.component.utils;

import java.util.ArrayList;
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
        if (args == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(args).collect(Collectors.toList());
    }
}
