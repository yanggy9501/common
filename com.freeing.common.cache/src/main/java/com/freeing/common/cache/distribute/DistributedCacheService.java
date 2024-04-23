package com.freeing.common.cache.distribute;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 分布式缓存接口
 */
public interface DistributedCacheService {
    void put(String key, String value);

    void put(String key, Object value);

    void put(String key, Object value, long timeout, TimeUnit unit);

    void put(String key, Object value, long expireTime);

    <T> T getObject(String key, Class<T> targetClass);

    String getString(String key);

    <T> List<T> getList(String key, Class<T> targetClass);

    boolean delete(String key);

    boolean deleteKeyPrefix(String prefix);

    boolean hasKey(String key);
}
