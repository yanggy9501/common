package com.freeing.common.support.pipeline.core;

import com.freeing.common.support.pipeline.core.handler.ProcessHandler;

public class DefaultProcessHandlerContext extends AbstractProcessHandlerContext {
    private final ProcessHandler processHandler;

    public DefaultProcessHandlerContext(ProcessPipeline pipeline, String name, ProcessHandler processHandler) {
        super(pipeline, name);
        this.processHandler = processHandler;
    }

    @Override
    public ProcessHandler handler() {
        return processHandler;
    }
}
