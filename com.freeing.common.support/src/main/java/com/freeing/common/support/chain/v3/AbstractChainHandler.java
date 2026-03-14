package com.freeing.common.support.chain.v3;

public abstract class AbstractChainHandler<T> implements ChainHandler<T> {

    @Override
    public void handle(ChainContext<T> context) {
        if (!match(context)) {
            return;
        }
        doHandle(context);
    }

    /**
     * 是否执行
     */
    protected boolean match(ChainContext<T> context) {
        return true;
    }

    /**
     * 具体逻辑
     */
    protected abstract void doHandle(ChainContext<T> context);

}