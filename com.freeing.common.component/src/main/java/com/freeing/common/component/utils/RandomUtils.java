package com.freeing.common.component.utils;

import java.util.UUID;

/**
 * 随机工具类
 *
 * @author yanggy
 */
public class RandomUtils {
    /**
     * 去掉 - 的 uuid 字符串
     *
     * @return simple uuid string
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 随机生成一个 uuid 字符串
     *
     * @return uuid string
     */
    public static String fastUUID() {
        return UUID.randomUUID().toString();
    }
}
