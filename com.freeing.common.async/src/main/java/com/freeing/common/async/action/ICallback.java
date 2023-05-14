package com.freeing.common.async.action;

import com.freeing.common.async.worker.WorkResult;

/**
 * 执行单元执行后的回调
 *
 * @param <T>
 * @param <V>
 */
@FunctionalInterface
public interface ICallback<T, V> {

    /**
     * 任务开始前的回调
     */
    default void beforeAction() {}

    /**
     * 任务执行完毕后
     *
     * @param param 入参
     * @param workResult 封装的结果
     */
    void afterFinish(T param, WorkResult<V> workResult);
}
