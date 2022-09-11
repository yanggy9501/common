package com.freeing.common.component.utils;

/**
 * @author yanggy
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {
    /**
     * 判断一个对象是否为 null
     *
     * @param obj 任意对象
     * @return boolean
     */
    public static <T> boolean isNull(T obj) {
        return obj == null;
    }
}
