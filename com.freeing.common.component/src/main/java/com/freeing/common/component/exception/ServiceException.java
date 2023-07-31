package com.freeing.common.component.exception;

/**
 * 基础异常
 */
public class ServiceException extends BaseException {
    public ServiceException(String code, String msg, Object... args) {
        super(code, msg, args);
    }

    public ServiceException(String code, Object... args) {
        super(code, args);
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException() {
    }
}
