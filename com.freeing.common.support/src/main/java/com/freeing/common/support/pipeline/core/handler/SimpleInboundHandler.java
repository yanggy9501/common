package com.freeing.common.support.pipeline.core.handler;

import com.freeing.common.support.pipeline.core.ProcessHandlerContext;

/**
 * SimpleInboundHandler: 参数类型转换
 *
 * process method：内部实现，模板调用
 * handle method：外部实现，业务逻辑，以及是否需要执行下一个 ProcessHandler
 * 执行链是否执行有开发决定：
 * 调用 context.fireProcess 则执行下一个 ProcessHandler
 *
 * 执行链条：process --> beforeProcess -> beforeHandle -> handle
 *
 * @param <T>
 */
public abstract class SimpleInboundHandler<T> extends ProcessInBoundHandlerAdapter {
    @Override
    public boolean beforeProcess(ProcessHandlerContext context, Object message) throws Exception {
        return beforeHandle(context, (T) message);
    }

    @Override
    public void process(ProcessHandlerContext context, Object message) throws Exception {
        if (beforeProcess(context, message)) {
            handle(context, (T) message);
        }
    }

    /**
     * implement by user or default true
     *
     * @param context context
     * @param inputParams user input params
     * @return false: stop this ProcessHandler
     * @throws Exception
     */
    public boolean beforeHandle(ProcessHandlerContext context, T inputParams) throws Exception {
        return Boolean.TRUE;
    }


    /**
     * implement by user
     * 抽象方法，用户实现业务逻辑，以及 context#fireProcess 继续执行下一个 ProcessHandler，就像 过滤器 Filter 一样。
     *
     * @param context context
     * @param inputParams user input params
     * @throws Exception
     */
    public abstract void handle(ProcessHandlerContext context, T inputParams) throws Exception;
}
