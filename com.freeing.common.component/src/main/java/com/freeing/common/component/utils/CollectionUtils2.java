package com.freeing.common.component.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author yanggy
 */
public class CollectionUtils2 {
    /**
     * 判断map是否为空（集合对象是否为null，大小是否为空）
     *
     * @param map map
     * @return boolean
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

    /**
     * 判断 map 是否不为空
     *
     * @param map map
     * @return boolean
     */
    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    /**
     * 将 Integer 集合转化为 int 数组，如果集合有 null 则过滤 null值
     *
     * @param collection Integer集合
     * @return int[]
     */
    public static int[] toIntArray(List<Integer> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return new int[0];
        }
        collection = collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
        int[] intArray = new int[collection.size()];
        for (int i = 0; i < collection.size(); i++) {
            intArray[i] = collection.get(i);
        }
        return intArray;
    }

    /**
     * 将 Long 集合转化为 long 数组，如果集合有 null 则过滤 null值
     *
     * @param collection Long集合
     * @return long[]
     */
    public static long[] toLongArray(List<Long> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return new long[0];
        }
        collection = collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
        long[] longArray = new long[collection.size()];
        for (int i = 0; i < collection.size(); i++) {
            longArray[i] = collection.get(i);
        }
        return longArray;
    }

    /**
     * List 转换为 Set，并排除空对象
     *
     * @param collection 集合
     * @return Set
     */
    public static <T> Set<T> toSetExcludeNull(List<T> collection) {
        if (collection == null) {
            return new HashSet<>();
        }
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }
}
