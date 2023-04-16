package com.freeing.common.support.factory;

import org.reflections.Reflections;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yanggy
 */
public class Factory {
    /**
     * 单例池
     * 使用 ConcurrentHashMap 解决同时存在又注册对象，又获取对象的情况
     */
    private final ConcurrentHashMap<Object, Object> SINGLETON_OBJECTS = new ConcurrentHashMap<>();

    /**
     * 扫描的包路径
     */
    private List<String> packages;

    /**
     * 所属的具体工厂的类型
     */
    private Class<?> factoryClassOf;

    /**
     * 配置文件的 classpath 路径（保存class类路径）
     */
    private List<String> propertiesFiles;

    /**
     * 加载配置文件的类加载器
     */
    private ClassLoader classLoader;

    /**
     * 其他额外的绝对路径的配置文件
     */
    private List<String> extraFiles;

    public Factory(List<String> extraFiles) {
        this.extraFiles = extraFiles;
    }

    public Factory(List<String> propertiesFiles, ClassLoader classLoader) {
        this.propertiesFiles = propertiesFiles;
        this.classLoader = classLoader;
    }

    public Factory(List<String> packages, Class<?> factoryClassOf) {
        this.packages = packages;
        this.factoryClassOf = factoryClassOf;
    }

    public Factory(List<String> packages,
            Class<?> factoryClassOf,
            List<String> propertiesFiles,
            ClassLoader classLoader,
            List<String> extraFiles) {
        this.packages = packages;
        this.factoryClassOf = factoryClassOf;
        this.propertiesFiles = propertiesFiles;
        this.classLoader = classLoader;
        this.extraFiles = extraFiles;
    }

    /**
     * 初始化
     */
    public synchronized void  init() {
        scanAbsolutePath();
        scanClassPath();
        scanPackages();
    }

    public Object get(String key) {
        Object bean = SINGLETON_OBJECTS.get(key);
        Objects.requireNonNull(bean, "The object is not existing.");
        return bean;
    }

    public <T> T get(Class<T> type) {
        Object bean = SINGLETON_OBJECTS.get(type);
        if (bean != null && bean.getClass().isAssignableFrom(type)) {
            return (T) bean;
        }
        return null;
    }

    private void scanClassPath() {
        if (propertiesFiles == null) {
            return;
        }
        for (String propertiesFile : propertiesFiles) {
            try (InputStream in = classLoader.getResourceAsStream(propertiesFile)) {
                Properties properties = new Properties();
                properties.load(in);
                properties.forEach((key, className) -> registry((String) key, (String) className));
            } catch (IOException ignored) {

            }
        }
    }

    private void scanAbsolutePath() {
        if (extraFiles == null) {
            return;
        }
        for (String extraFile : extraFiles) {
            try (FileReader in = new FileReader(extraFile)) {
                Properties properties = new Properties();
                properties.load(in);
                properties.forEach((key, className) -> registry((String) key, (String) className));
            } catch (IOException ignored) {

            }
        }
    }

    private void scanPackages() {
        if (packages == null || packages.size() == 0) {
            return;
        }
        for (String aPackage : packages) {
            Reflections reflections = new Reflections(aPackage);
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(FactoryType.class);
            for (Class<?> clazz : classes) {
                FactoryType factoryType = clazz.getAnnotation(FactoryType.class);
                String key = factoryType.value() == null || factoryType.value().equals("") ?
                    clazz.getCanonicalName() : factoryType.value();
                Class<?> ofFactory = factoryType.of() == null ? Factory.class : factoryType.of();
                if (ofFactory != factoryClassOf) {
                    continue;
                }
                registry(key, clazz);
            }
        }
    }

    public void registry(String key, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            registry(key, clazz);
        } catch (ClassNotFoundException ignored) {

        }
    }

    public void registry(String key, Class<?> clazz) {
        if (SINGLETON_OBJECTS.containsKey(key)) {
            return;
        }
        try {
            Constructor<?> constructor = clazz.getConstructor();
            Object bean = constructor.newInstance();
            SINGLETON_OBJECTS.put(key, bean);
            SINGLETON_OBJECTS.put(clazz, bean);
        }  catch (NoSuchMethodException
                  | InstantiationException
                  | InvocationTargetException
                  | IllegalAccessException ignored) {

        }
    }
}
