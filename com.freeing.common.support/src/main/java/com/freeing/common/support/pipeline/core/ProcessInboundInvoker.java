package com.freeing.common.support.pipeline.core;

public interface ProcessInboundInvoker {
    /**
     * @param message message
     */
    void fireProcess(Object  message);
}
