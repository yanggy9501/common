package com.freeing.common.support.factory;

import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author yanggy
 */
public class Factory {
    /**
     * 单例池
     */
    private final Map<Object, Object> SINGLETON_OBJECTS = new HashMap<>();

    /**
     * 扫描的包路径
     */
    private List<String> packages;

    /**
     * 具体工厂的类型
     */
    private Class<?> factoryClass;

    /**
     * 配置文件的 classpath 路径
     */
    private List<String> propertiesFiles;

    /**
     * 加载配置文件的类加载器
     */
    private ClassLoader classLoader;

    public Factory(List<String> packages, Class<?> factoryClass, List<String> propertiesFiles, ClassLoader classLoader) {
        this.packages = packages;
        this.factoryClass = factoryClass;
        this.propertiesFiles = propertiesFiles;
        this.classLoader = classLoader;
    }

    /**
     * 初始化
     */
    public void init() {
        refresh();
    }

    public Object get(String key) {
        Object bean = SINGLETON_OBJECTS.get(key);
        Objects.requireNonNull(bean, "The object is not existing.");
        return bean;
    }

    public <T> T get(Class<T> type) {
        return get(type);
    }

    /**
     * 刷新方法
     */
    public void refresh() {
        scanClassPath();
        scanPackages();
    }

    private void scanClassPath() {
        for (String propertiesFile : propertiesFiles) {
            InputStream in = classLoader.getResourceAsStream(propertiesFile);
            Properties properties = new Properties();
            try {
                properties.load(in);
                properties.forEach((key, className) -> registry((String) key, (String) className));
            } catch (IOException e) {

            }
        }
    }

    private void scanPackages() {
        for (String aPackage : packages) {
            Reflections reflections = new Reflections(aPackage);
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(FactoryType.class);
            for (Class<?> clazz : classes) {
                FactoryType factoryType = clazz.getAnnotation(FactoryType.class);
                String key = factoryType.value() == null ? clazz.getCanonicalName() : factoryType.value();
                Class<?> ofFactory = factoryType.of() == null ? Factory.class : factoryType.of();
                if (ofFactory != factoryClass) {
                    continue;
                }
                registry(key, clazz);
            }
        }
    }

    public void registry(String key, String className) {
        if (SINGLETON_OBJECTS.containsKey(key)) {
            // key 已经存在
        }
        try {
            Class<?> clazz = Class.forName(className);
            registry(key, clazz);
        } catch (ClassNotFoundException e) {

        }
    }

    public void registry(String key, Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getConstructor();
            Object bean = constructor.newInstance();
            SINGLETON_OBJECTS.put(key, bean);
        }  catch (NoSuchMethodException e) {

        } catch (InvocationTargetException e) {

        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
    }
}
