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
     * @param allWrapperMap “所有”前置的任务包装，若是 isMust 则执行 action 时 allWrapperMap 中必然存在，否则执行时不一定存在。
     *                      从 allWrapperMap 获取其他任务时，应该保证前置依赖时 isMust 或者，只有一条路径到该节点。
     *                      若 allWrapperMap 中能获取到某个结果则，这个结果没有可见性问题，因为 allWrapperMap 只有保存一次，
     *                      且保存的对象不会在修改
     *
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
