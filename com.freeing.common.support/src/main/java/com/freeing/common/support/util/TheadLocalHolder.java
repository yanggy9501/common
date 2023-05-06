package com.freeing.common.support.util;

import javax.management.openmbean.InvalidKeyException;

/**
 * @author yanggy
 */
public class TheadLocalHolder<T> {
    private final ThreadLocal<T> threadLocal = new ThreadLocal<>();

    public T get() {
        T data = threadLocal.get();
        if (data != null) {
            return data;
        }
        throw new InvalidKeyException("Invalid key of ThreadLocal or ThreadLocal#get is null");
    }

    public void set(T data) {
        if (data == null) {
            throw new NullPointerException("Data of ThreadLocal#set is can not null");
        }
        threadLocal.set(data);
    }
}
