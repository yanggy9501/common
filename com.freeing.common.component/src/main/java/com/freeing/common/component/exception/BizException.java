package com.freeing.common.component.exception;

/**
 * 基础异常
 */
public class BizException extends RuntimeException {
    /**
     * I18n KEY
     */
    private String tag;

    /**
     * 异常代码
     */
    private String code;

    /**
     * 异常时入参参数
     */
    private Object[] args;

    public BizException(String tag, String code, Object ...args) {
        this.tag = tag;
        this.code = code;
        this.args = args;
    }

    public BizException(String tag, String code) {
        this.code = code;
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
}
