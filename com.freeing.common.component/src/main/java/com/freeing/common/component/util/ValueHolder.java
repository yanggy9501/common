package com.freeing.common.component.util;

/**
 * value 容器
 *
 * @author yanggy
 */
public class ValueHolder<T> {

    private T value;

    public ValueHolder() {
    }

    public ValueHolder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
