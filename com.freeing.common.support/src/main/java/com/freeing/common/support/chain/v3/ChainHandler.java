package com.freeing.common.support.chain.v3;

public interface ChainHandler<T> {
    /**
     * 执行处理
     */
    void handle(ChainContext<T> context);
}