package com.freeing.common.component.util.id;

import java.util.UUID;

/**
 * UUID 工具类
 *
 * @author yanggy
 */
public class UUIDUtils {

    public static String fastUUID() {
        return UUID.randomUUID().toString();
    }

    public static String simpleUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
