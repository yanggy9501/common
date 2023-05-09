package com.freeing.common.async.action;

import com.freeing.common.async.worker.WorkResult;

/**
 * 默认回调类，如果不设置的话，会默认给这个回调
 */
public class DefaultCallback<T, V> implements ICallback<T, V> {
    @Override
    public void beforeStart() {
        
    }

    @Override
    public void afterFinish(T param, WorkResult<V> workResult) {

    }
}
