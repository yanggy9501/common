package com.freeing.common.support.pipeline.core;

import com.freeing.common.support.pipeline.core.handler.ProcessHandler;
import com.freeing.common.support.pipeline.core.handler.ProcessInBoundHandler;
import com.freeing.common.support.pipeline.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultProcessPipeline implements ProcessPipeline {

    private static final ThreadLocal<Map<Class<?>, String>> NAME_CACHES = ThreadLocal.withInitial(() -> new ConcurrentHashMap<>(32));
    final AbstractProcessHandlerContext head;
    final AbstractProcessHandlerContext tail;
    final String headName = "head";
    final String tailName = "tail";
    Object resultKey;

    public DefaultProcessPipeline() {
        head = new HeadContext(this, headName);
        tail = new TailContext(this, tailName);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public ProcessPipeline addLast(String name, ProcessHandler processHandler) {
        final AbstractProcessHandlerContext newCtx;
        synchronized (this) {
            newCtx = newContext(filterName(name, processHandler), processHandler);
            addLast0(newCtx);
        }
        return this;
    }

    private void addLast0(AbstractProcessHandlerContext newCtx) {
        AbstractProcessHandlerContext prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;
    }

    private DefaultProcessHandlerContext newContext(String name, ProcessHandler processHandler) {
        return new DefaultProcessHandlerContext(this, name, processHandler);
    }

    private String filterName(String name, ProcessHandler handler) {
        if (name == null) {
            return generateName(handler);
        }
        checkDuplicateName(name);
        return name;
    }

    private void checkDuplicateName(String name) {
        if (context0(name) != null) {
            throw new IllegalArgumentException("Duplicate handler name: " + name);
        }
    }

    private String generateName(ProcessHandler handler) {
        Map<Class<?>, String> cache = NAME_CACHES.get();
        Class<?> handlerType = handler.getClass();
        String name = cache.get(handlerType);
        if (name == null) {
            name = generateName0(handlerType);
            cache.put(handlerType, name);
        }

        if (context0(name) != null) {
            String baseName = name.substring(0, name.length() - 1);
            for (int i = 1; ; i++) {
                String newName = baseName + i;
                if (context0(newName) == null) {
                    name = newName;
                    break;
                }
            }
        }
        return name;
    }

    private static String generateName0(Class<?> handlerType) {
        return StringUtil.simpleClassName(handlerType) + "#0";
    }

    private AbstractProcessHandlerContext context0(String name) {
        AbstractProcessHandlerContext context = head.next;
        while (context != tail) {
            if (context.name().equals(name)) {
                return context;
            }
            context = context.next;
        }
        return null;
    }

    @Override
    public void fireProcess(Object message) {
        AbstractProcessHandlerContext.invokeProcess(head, message);
    }

    final class HeadContext extends AbstractProcessHandlerContext implements ProcessInBoundHandler {

        HeadContext(DefaultProcessPipeline pipeline, String name) {
            super(pipeline, name);
        }

        @Override
        public boolean beforeProcess(ProcessHandlerContext ctx, Object msg) {
            return true;
        }

        @Override
        public void process(ProcessHandlerContext context, Object message) throws Exception {
            context.fireProcess(message);
        }

        @Override
        public void exceptionCaught(ProcessHandlerContext ctx, Throwable cause) {
            throw new RuntimeException(cause);
        }

        @Override
        public ProcessHandler handler() {
            return this;
        }
    }


    final class TailContext extends AbstractProcessHandlerContext
        implements ProcessInBoundHandler {
        TailContext(DefaultProcessPipeline pipeline, String name) {
            super(pipeline, name);
        }


        @Override
        public boolean beforeProcess(ProcessHandlerContext ctx, Object msg) {
            return true;
        }

        @Override
        public void process(ProcessHandlerContext context, Object message) {
            context.fireProcess(message);
        }


        @Override
        public void exceptionCaught(ProcessHandlerContext ctx, Throwable cause) {
            throw new RuntimeException(cause);
        }

        @Override
        public ProcessHandler handler() {
            return this;
        }
    }
}
