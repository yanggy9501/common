package com.freeing.common.component.constant;

/**
 * 返回状态码
 * 操作成功：1
 * 操作失败：-1
 *
 * @author yanggy
 */
public enum ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(20000, "Operation success"),

    /**
     * 操作失败
     */
    FAIL(-1, "Operation fail"),

    /**
     * 重复提交
     */
    REPEAT_SUBMIT(20100, "Repeat submit"),

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

    ResultCode(Integer code, String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode get(Integer code) {
        for (ResultCode value : ResultCode.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Illegal R code '" + code + "'");
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
