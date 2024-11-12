package com.freeing.common.component.constant;

/**
 * 返回状态码
 * 原则：2 + 3 位状态码，前 2 位代表模块，后 3 位代码具体“状态”
 *
 * @author yanggy
 */
public enum ResponseCode {
    /**
     * 操作成功
     */
    SUCCESS(1, "Operation success"),

    /**
     * 操作失败
     */
    FAIL(0, "Operation fail"),

    /**
     * 重复提交
     */
    REPEAT_SUBMIT(20200, "Repeat submit"),

    /**
     * Exception
     */
    ERROR(50000, "Unknown exception");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态信息
     */
    private final String msg;

    ResponseCode(Integer code, String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseCode get(Integer code) {
        for (ResponseCode value : ResponseCode.values()) {
            if (value.code().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Illegal R code '" + code + "'");
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
