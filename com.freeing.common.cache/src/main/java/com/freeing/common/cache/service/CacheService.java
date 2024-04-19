package com.freeing.common.cache.service;

/**
 * 通用缓存接口
 */
public interface CacheService {
    /**
     * 构建缓存的 key
     */
    default String buildCacheKey(Object key){
        return key == null ? "" : key.toString();
    }
}
