package com.freeing.common.component.constant;

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
    SUCCESS("200", "Operation success"),

    /**
     * 操作失败
     */
    FAIL("-1", "Operation fail"),

    /**
     * 重复提交
     */
    REPEAT_SUBMIT("201", "Repeat submit"),

    /**
     * Exception
     */
    ERROR("-2", "Unknown exception");

    /**
     * 状态码
     */
    private final String code;

    /**
     * 状态信息
     */
    private final String msg;

    ResultCode(String code, String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode get(String code) {
        for (ResultCode value : ResultCode.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Illegal R code '" + code + "'");
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
