package com.freeing.common.support.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * LUR cache 实现
 * TODO 线程安全吗？
 *
 * @author yanggy
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    /**
     * 缓存容量
     */
    private final int capacity;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    public LRUCache(int capacity) {
        // lur accessOrder 设置为 true，按访问顺序排序
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    /**
     * 是否移除最近最不经常访问的元素（即最老的元素，默认是 false）
     *
     * @return boolean
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > capacity;
    }

    @Override
    public V get(Object key) {
        readLock.lock();
        try {
            return super.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        writeLock.lock();
        try {
            return super.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}
