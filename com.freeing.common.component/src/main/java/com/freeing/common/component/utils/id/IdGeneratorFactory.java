package com.freeing.common.component.utils.id;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ID 生成器工厂
 *
 * @author yanggy
 */
public class IdGeneratorFactory {
    private final ConcurrentHashMap<Class<?>, IdGenerator> idGeneratorMap = new ConcurrentHashMap<>();

    private IdGeneratorFactory() {

    }

    public IdGenerator getIdGenerator(Class<?> clazz) {
        return idGeneratorMap.compute(clazz, (key, value) ->  new IdGenerator(1L, 1L));
    }
}
