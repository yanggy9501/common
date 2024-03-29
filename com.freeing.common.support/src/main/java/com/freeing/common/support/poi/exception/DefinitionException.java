package com.freeing.common.support.poi.exception;

/**
 * @author yanggy
 */
public class DefinitionException extends RuntimeException {
    public DefinitionException() {
    }

    public DefinitionException(String message) {
        super(message);
    }

    public DefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefinitionException(Throwable cause) {
        super(cause);
    }

    public DefinitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
