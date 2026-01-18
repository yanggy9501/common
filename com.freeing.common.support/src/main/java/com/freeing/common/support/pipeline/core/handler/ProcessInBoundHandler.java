package com.freeing.common.support.pipeline.core.handler;

import com.freeing.common.support.pipeline.core.ProcessHandlerContext;

public interface ProcessInBoundHandler extends ProcessHandler {
    /**
     * inner use method
     *
     * @param context this ProcessHandler context
     * @param message message
     * @return boolean
     */
    boolean beforeProcess(ProcessHandlerContext context, Object message) throws Exception;

    /**
     * inner use method
     *
     * @param context context
     * @param message message
     * @throws Exception
     */
    void process(ProcessHandlerContext context, Object message) throws Exception;

    /**
     * @param context context
     * @param cause throwable
     */
    void exceptionCaught(ProcessHandlerContext context, Throwable cause);
}
