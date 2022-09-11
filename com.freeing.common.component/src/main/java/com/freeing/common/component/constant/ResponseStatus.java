package com.freeing.common.component.constant;

/**
 * 返回状态码
 * 操作成功：0
 * 操作失败：-1
 * 未知异常：-2
 * 其他状态码 = 模块编码 + 异常编码
 */
public enum ResponseStatus {
    /**
     * 操作成功
     */
    SUCCESS(0, "Operation success"),

    /**
     * 操作失败
     */
    FAIL(-1, "Operation fail"),

    /**
     * error
     */
    ERROR(-2, "Unknow error");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态信息
     */
    private final String msg;

    ResponseStatus(int code, String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
