package com.freeing.common.support.xml.exception;

/**
 * xml 异常
 *
 * @author yanggy
 */
public class BuilderException extends RuntimeException {
    public BuilderException() {
        super();
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }
}
