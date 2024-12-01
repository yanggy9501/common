package com.freeing.common.cache.local.decorators;

import com.freeing.common.cache.local.Cache;

import java.util.LinkedList;

public class FifoCache<K, V> implements Cache<K, V> {
    private final Cache<K, V> delegate;

    private final LinkedList<K> keyList;

    private int threshold;

    public FifoCache(Cache<K, V> delegate) {
        this.delegate = delegate;
        // 先进先出的的实现通过LinkedList
        this.keyList = new LinkedList<>();
        this.threshold = 1024;
    }

    public FifoCache(Cache<K, V> delegate, int threshold) {
        this.delegate = delegate;
        // 先进先出的的实现通过LinkedList
        this.keyList = new LinkedList<>();
        this.threshold = threshold;
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public void putObject(K key, V value) {
        cycleKeyList(key);
        delegate.putObject(key, value);
    }

    private void cycleKeyList(K key) {
        keyList.addLast(key);
        if (keyList.size() > threshold) {
            K oldestKey = keyList.removeFirst();
            delegate.removeObject(oldestKey);
        }
    }

    @Override
    public V getObject(K key) {
        return delegate.getObject(key);
    }

    @Override
    public V removeObject(K key) {
        return delegate.removeObject(key);
    }

    @Override
    public void clear() {
        delegate.clear();
        keyList.clear();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }
}
