package com.freeing.common.support.pipeline.core;

import com.freeing.common.support.pipeline.core.handler.ProcessInBoundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractProcessHandlerContext implements ProcessHandlerContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcessHandlerContext.class);
    // 暂时没有用到
    private final ProcessPipeline pipeline;
    private final String name;
    volatile AbstractProcessHandlerContext next;
    volatile AbstractProcessHandlerContext prev;
    ThreadLocal<Map<Object, Object>> attach = ThreadLocal.withInitial(() -> new ConcurrentHashMap<>(32));

    public AbstractProcessHandlerContext(ProcessPipeline pipeline, String name) {
        assert name != null;
        assert pipeline != null;
        this.pipeline = pipeline;
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public ProcessPipeline pipeline() {
        return pipeline;
    }

    @Override
    public void attach(Map map) {
        attach.set(map);
    }

    @Override
    public Map getAttach() {
        return attach.get();
    }

    @Override
    public void fireProcess(Object message) {
        invokeProcess(findContextInBound(), message);
    }

    private AbstractProcessHandlerContext findContextOutBound() {
        AbstractProcessHandlerContext ctx = this;
        return ctx.prev;
    }

    /**
     * find next HandlerContext in ProcessPipeline
     * @return AbstractProcessHandlerContext
     */
    private AbstractProcessHandlerContext findContextInBound() {
        AbstractProcessHandlerContext context = this;
        return context.next;
    }

    /**
     * 外部调用：
     * 执行 ProcessHandlerContext 包装的 ProcessHandler
     *
     * @param contextInBound contextInBound
     * @param message message
     */
    static void invokeProcess(AbstractProcessHandlerContext contextInBound, Object message) {
        contextInBound.doInvokeProcess(message);
    }

    private void doInvokeProcess(Object message) {
        try {
            ((ProcessInBoundHandler) handler()).process(this, message);
        } catch (Throwable t) {
            fireException(t);
        } finally {
            this.attach.remove();

        }
    }

    /**
     * context 从后往前抛出异常，但是如果前面的handler实现了 exceptionCaught
     * 这样后面的 handler 的异常 throwable 就会被前面的 handler 的 catch 方法 catch 住，这不合理。
     * 所以 handler 抛出异常后，进行包装成特定的异常 `PipelineProcessException`, 属于特定异常时一直抛出
     * @param throwable
     */
    private void fireException(Throwable throwable) {
        if (throwable instanceof PipelineProcessException) {
            throw new PipelineProcessException(throwable);
        } else {
            invokeExceptionCaught(throwable);
        }
    }

    private void invokeExceptionCaught(Throwable cause) {
        try {
            ((ProcessInBoundHandler) handler()).exceptionCaught(this, cause);
        } catch (Throwable error) {
            throw new PipelineProcessException(error);
        }
    }
}
