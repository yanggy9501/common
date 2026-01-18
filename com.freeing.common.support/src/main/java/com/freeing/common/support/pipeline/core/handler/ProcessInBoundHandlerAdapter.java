package com.freeing.common.support.pipeline.core.handler;

import com.freeing.common.support.pipeline.core.PipelineProcessException;
import com.freeing.common.support.pipeline.core.ProcessHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class ProcessInBoundHandlerAdapter implements ProcessInBoundHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessInBoundHandlerAdapter.class);

    @Override
    public boolean beforeProcess(ProcessHandlerContext context, Object message) throws Exception {
        return true;
    }

    @Override
    public void exceptionCaught(ProcessHandlerContext context, Throwable cause) {
        LOGGER.error("User handler is error, Please implements exceptionCaught to handle the error", cause);
        throw new PipelineProcessException(cause);
    }
}
