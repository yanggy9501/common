package com.freeing.common.component.utils.queue.exception;

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
