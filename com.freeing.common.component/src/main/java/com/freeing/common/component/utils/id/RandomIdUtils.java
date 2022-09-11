package com.freeing.common.component.utils.id;

import java.util.UUID;

/**
 * uuid 工具类
 *
 * @author yanggy
 */
public class RandomIdUtils {

    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String fastUUID() {
        return UUID.randomUUID().toString();
    }
}
