package com.freeing.common.component.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回状态码
 * 操作成功：1
 * 操作失败：-1
 * 未知异常：-2
 *
 * @author yanggy
 */
public enum ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "Operation success"),

    /**
     * 操作失败
     */
    FAIL(-1, "Operation fail"),

    /**
     * 重复提交
     */
    REPEAT_SUBMIT(201, "Repeat submit"),

    /**
     * Exception
     */
    ERROR(-2, "Unknown exception");

    private static final Map<Integer, ResultCode> cache;

    static {
        cache = new HashMap<>();
        for (ResultCode value : ResultCode.values()) {
            cache.put(value.getCode(), value);
        }
    }

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态信息
     */
    private final String msg;

    ResultCode(int code, String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode get(int code) {
        return cache.get(code);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
