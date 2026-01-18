package com.freeing.common.support.pipeline.core;

import com.freeing.common.support.pipeline.core.handler.ProcessHandler;

import java.util.Map;

/**
 * ProcessHandler 上下文
 *
 * 1. 链接 ProcessHandler 前驱后继节点，组成双向链表结果
 * 2. 提供 ProcessHandler 执行方法
 */
public interface ProcessHandlerContext extends ProcessInboundInvoker {
    String name();

    ProcessHandler handler();

    ProcessPipeline pipeline();

    void attach(Map map);

    Map getAttach();
}
