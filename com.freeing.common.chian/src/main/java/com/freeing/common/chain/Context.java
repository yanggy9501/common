package com.freeing.common.chain;

import java.util.HashMap;

/**
 * 链路上下文
 * 责任链执行过程中，共享数据通过 Context 从上一个 Command 传递给下一个 Command
 * 链路中断：当某个 Command 中调用 stop() 方法，则后续 Command#execute 不再执行。
 */
public abstract class Context extends HashMap<String, Object> {
    /**
     * 是否终止执行链路
     */
    private boolean stop = false;

    public Context() {
        super();
    }

    public void stop() {
        this.stop = true;
    }

    public boolean isStop() {
        return stop;
    }
}
