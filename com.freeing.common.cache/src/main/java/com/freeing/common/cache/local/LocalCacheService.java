package com.freeing.common.cache.local;

/**
 * 本地缓存服务接口
 *
 * @param <K> key
 * @param <V> value
 */
public interface LocalCacheService<K, V> {
    void put(K key, V value);

    V getIfPresent(Object key);

    void delete(Object key);
}
