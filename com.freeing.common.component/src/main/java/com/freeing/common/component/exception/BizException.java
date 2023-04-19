package com.freeing.common.component.exception;

/**
 * 基础异常
 */
public class BizException extends RuntimeException {
    /**
     * 错误码
     */
    private String tag;

    /**
     * 异常时入参参数
     */
    private Object[] args;

    public BizException(String tag, Object ...args) {
        this.tag = tag;
        this.args = args;
    }

    public BizException(String tag) {
        this.tag = tag;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
