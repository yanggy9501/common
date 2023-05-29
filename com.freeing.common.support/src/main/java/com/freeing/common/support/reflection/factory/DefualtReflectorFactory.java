package com.freeing.common.support.reflection.factory;

import com.freeing.common.support.reflection.Reflector;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yanggy
 */
public class DefualtReflectorFactory {

    private boolean enableCache;

    private final ConcurrentHashMap<Class<?>, Reflector> cache = new ConcurrentHashMap<>();

    public DefualtReflectorFactory() {
        this.enableCache = true;
    }

    public DefualtReflectorFactory(boolean enableCache) {
        this.enableCache = enableCache;
    }

    public Reflector getReflector(Class<?> type) {
        if (enableCache) {
            return cache.computeIfAbsent(type, Reflector::new);
        }
        return new Reflector(type);
    }
}
