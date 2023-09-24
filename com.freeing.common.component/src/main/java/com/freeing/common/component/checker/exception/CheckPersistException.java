package com.freeing.common.component.checker.exception;

/**
 * @author yanggy
 */
public class CheckPersistException extends RuntimeException {
    public CheckPersistException(String message) {
        super(message);
    }

    public CheckPersistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckPersistException(Throwable cause) {
        super(cause);
    }

    public CheckPersistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
