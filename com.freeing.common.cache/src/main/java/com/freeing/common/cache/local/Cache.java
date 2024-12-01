package com.freeing.common.cache.local;

public interface Cache<K, V> {
    /**
     * @return The identifier of this cache
     */
    String getId();

    void putObject(K key, V value);

    V getObject(K key);

    V removeObject(K key);

    void clear();

    int getSize();
}
