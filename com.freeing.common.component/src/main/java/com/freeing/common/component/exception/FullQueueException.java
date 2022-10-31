package com.freeing.common.component.exception;

/**
 * @author yanggy
 */
public class FullQueueException extends RuntimeException {
    public FullQueueException() {

    }

    public FullQueueException(String message) {
        super(message);
    }
}
