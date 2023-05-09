package com.freeing.common.async.action;

import com.freeing.common.async.wrapper.WorkerWrapper;

import java.util.Map;

/**
 * 最小执行单元的规范接口
 */
@FunctionalInterface
public interface IWorker<T, V> {

    /**
     * 在这里做耗时操作，如rpc请求、IO等
     *
     * @param object 入参，若入参是 WorkResult 的类型即可实现不同类型在上下游的传递，这种方式是预先占用的方式
     * @param allWrapperMap 所有的任务包装
     */
    V action(T object, Map<String, WorkerWrapper<?, ?>> allWrapperMap);

    /**
     * 超时、异常时的默认返回值值
     *
     * @return 默认值
     */
    default V defaultValue() {
        return null;
    }
}
