package com.freeing.common.support.pipeline.core;

import com.freeing.common.support.pipeline.core.handler.ProcessHandler;

public interface ProcessPipeline extends ProcessInboundInvoker {
    ProcessPipeline addLast(String name, ProcessHandler processHandler);
}
