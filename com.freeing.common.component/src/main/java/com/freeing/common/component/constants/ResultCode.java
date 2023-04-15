package com.freeing.common.component.constants;

/**
 * 返回状态码
 * 操作成功：0
 * 操作失败：-1
 * 未知异常：-2
 * 其他状态码 = 模块编码 + 异常编码
 *
 * @author yanggy
 */
public enum ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(0, "Operation success"),

    /**
     * 操作失败
     */
    FAIL(-1, "Operation fail"),

    /**
     * Exception
     */
    ERROR(-2, "Unknown exception");

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

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
