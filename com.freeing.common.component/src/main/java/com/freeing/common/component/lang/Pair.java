package com.freeing.common.component.lang;

import java.io.Serializable;

/**
 * key-value 键值对
 *
 * @author yanggy
 */
public class Pair<K, V> implements Serializable {

    private K key;

    private V value;

    public Pair() {
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int result = getKey().hashCode();
        result = 31 * result + getValue().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!getKey().equals(pair.getKey())) return false;
        return getValue().equals(pair.getValue());
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
