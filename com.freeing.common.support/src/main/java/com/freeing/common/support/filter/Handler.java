package com.freeing.common.support.filter;

/**
 * @author yanggy
 */
public abstract class Handler<T> {

    public void doHandle(T obj, HandlerChain<T> chain) {
        // 执行下一个
        chain.doHandle(obj);
    }
}
