package com.freeing.common.support.chain.v3;

import java.util.HashMap;
import java.util.Map;

/**
 * 责任链上下问：
 * 链路中断
 * 数据共享
 * 上下文传递
 */
public class ChainContext<T> {

    private final T data;

    private boolean stop = false;

    private final Map<String, Object> attributes = new HashMap<>();

    public ChainContext(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void stop() {
        this.stop = true;
    }

    public boolean isStop() {
        return stop;
    }

    public void setAttr(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttr(String key) {
        return attributes.get(key);
    }
}
