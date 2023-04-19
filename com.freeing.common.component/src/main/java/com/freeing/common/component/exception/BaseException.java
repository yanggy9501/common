package com.freeing.common.component.exception;

/**
 * 基础异常
 */
public class BaseException extends RuntimeException {
    /**
     * 错误码
     */
    private String code;

    /**
     * 异常时入参参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String msg;

    public BaseException(String code, String msg, Object... args) {
        this.code = code;
        this.msg = msg;
        this.args = args;
    }

    public BaseException(String code, Object... args) {
        this(code, null, args);
    }


    public BaseException(String msg) {
        this.msg = msg;
    }

    public BaseException() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
