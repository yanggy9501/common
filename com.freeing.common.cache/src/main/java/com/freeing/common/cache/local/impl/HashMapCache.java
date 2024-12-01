package com.freeing.common.cache.local.impl;

import com.freeing.common.cache.local.Cache;

import java.util.HashMap;
import java.util.Map;

public class HashMapCache<K, V> implements Cache<K, V> {
    /**
     * 命名空间
     */
    private final String id;

    private final Map<K, V> cache = new HashMap<>();

    public HashMapCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V getObject(K key) {
        return cache.get(key);
    }

    @Override
    public V removeObject(K key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public boolean equals(Object o) {
        if (getId() == null) {
            throw new IllegalStateException("Cache instances require an ID.");
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cache)) {
            return false;
        }

        Cache otherCache = (Cache) o;
        return getId().equals(otherCache.getId());
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            throw new IllegalStateException("Cache instances require an ID.");
        }
        return getId().hashCode();
    }
}
