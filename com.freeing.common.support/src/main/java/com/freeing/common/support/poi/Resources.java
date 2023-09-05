package com.freeing.common.support.poi;

import java.io.InputStream;

/**
 * 资源工具类
 */
public class Resources {

    /**
     * 获取资源
     *
     * @param resource 资源
     * @param classLoader 类加载器
     * @return
     */
    public static InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
        ClassLoader[] classLoaders =  getClassLoader(classLoader);
        for (ClassLoader loader : classLoaders) {
            if (loader != null) {
                InputStream returnValue = loader.getResourceAsStream(resource);
                if (returnValue == null) {
                    returnValue = loader.getResourceAsStream("/" + resource);
                }
                if (returnValue != null){
                    return returnValue;
                }
            }
        }
        return null;
    }

    private static ClassLoader[] getClassLoader(ClassLoader classLoader) {
        return new ClassLoader[] {
            classLoader,
            Thread.currentThread().getContextClassLoader(),
            ClassLoader.getSystemClassLoader()
        };
    }
}
