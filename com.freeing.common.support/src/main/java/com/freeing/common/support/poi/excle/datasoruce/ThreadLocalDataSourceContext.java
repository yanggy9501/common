package com.freeing.common.support.poi.excle.datasoruce;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ThreadLocalDataSourceContext {
    private final static ThreadLocal<HashMap<String, List<Object>>>
        DATA_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public static void add(@Nonnull String key, List<Object> dataSource) {
        HashMap<String, List<Object>> dataMap = DATA_CONTEXT_THREAD_LOCAL.get();
        if (dataMap == null) {
            DATA_CONTEXT_THREAD_LOCAL.set(new HashMap<>());
        }
        DATA_CONTEXT_THREAD_LOCAL.get().put(key, dataSource);
    }

    public static List<Object> get(@Nonnull String key) {
        HashMap<String, List<Object>> dataMap = DATA_CONTEXT_THREAD_LOCAL.get();
        if (dataMap == null) {
            return Collections.emptyList();
        }
        return dataMap.getOrDefault(key, Collections.emptyList());
    }

    public static void clear() {
        DATA_CONTEXT_THREAD_LOCAL.remove();
    }
}
