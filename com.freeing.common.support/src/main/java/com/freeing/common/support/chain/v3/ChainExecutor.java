package com.freeing.common.support.chain.v3;

import java.util.List;

public class ChainExecutor<T> {

    private final List<ChainHandler<T>> handlers;

    public ChainExecutor(List<ChainHandler<T>> handlers) {
        this.handlers = handlers;
    }

    public void execute(ChainContext<T> context) {
        for (ChainHandler<T> handler : handlers) {
            if (context.isStop()) {
                break;
            }
            handler.handle(context);
        }
    }
}
