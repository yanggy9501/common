package com.freeing.common.component.exception;

/**
 * @author yanggy
 */
public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException() {
    }

    public EmptyQueueException(String message) {
        super(message);
    }
}
