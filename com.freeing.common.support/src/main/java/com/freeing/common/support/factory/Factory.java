package com.freeing.common.support.factory;

import org.reflections.Reflections;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 抽象的工厂，子类提供获取单例的实现并且调用初始化方法完成初始化
 *
 * @author yanggy
 */
public abstract class Factory {
    /**
     * 池
     */
    private final HashMap<Object, Object> OBJECT_POOL = new HashMap<>();

    /**
     * 单例
     */
    private final Set<String> SINGLETON_SET = new HashSet<>();

    /**
     * 扫描的包路径
     */
    private List<String> packages;

    /**
     * 配置文件的 classpath 路径（保存class类路径 properties 文件）
     */
    private List<String> propertiesFiles;

    /**
     * 其他额外的绝对路径的配置文件（properties 文件）
     */
    private List<String> extraFiles;

    /**
     * 所属的具体工厂的类型
     */
    private Class<?> factoryClassOf;

    /**
     * 加载配置文件的类加载器
     */
    private ClassLoader classLoader;

    private boolean init;

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
     * 获取单例对象
     *
     * @return Factory
     */
    public abstract Factory getInstance();

    /**
     * 初始化
     */
    protected synchronized void init() {
        if (init) {
            return;
        }
        scanAbsolutePath();
        scanClassPath();
        scanPackages();
        init = true;
    }

    public Object get(String key) {
        if (SINGLETON_SET.contains(key)) {
            return OBJECT_POOL.get(key);
        }
        Constructor<?> constructor = (Constructor<?>) OBJECT_POOL.get(key);
        try {
            return constructor.newInstance();
        } catch (Exception ignored) {
            return null;
        }
    }

    public <T> T get(Class<T> type) {
        Object bean = OBJECT_POOL.get(type);
        if (SINGLETON_SET.contains(type.getCanonicalName())) {
            return (T) bean;
        }
        Constructor<?> constructor = (Constructor<?>) bean;
        try {
            return (T) constructor.newInstance();
        } catch (Exception ignored) {
            return null;
        }
    }

    private void scanClassPath() {
        if (propertiesFiles == null) {
            return;
        }
        for (String propertiesFile : propertiesFiles) {
            try (InputStream in = classLoader.getResourceAsStream(propertiesFile)) {
                Properties properties = new Properties();
                properties.load(in);
                properties.forEach((key, className) -> registry((String) key, (String) className, true));
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
                properties.forEach((key, className) -> registry((String) key, (String) className, true));
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
                boolean isSingleton = factoryType.singleton();
                Class<?> ofFactory = factoryType.of() == null ? Factory.class : factoryType.of();
                if (ofFactory != factoryClassOf) {
                    continue;
                }
                registry(key, clazz, isSingleton);
            }
        }
    }

    public void registry(String key, String className, boolean isSingleton) {
        try {
            Class<?> clazz = Class.forName(className);
            registry(key, clazz, isSingleton);
        } catch (ClassNotFoundException ex) {
            throw new ReflectionsException(ex);
        }
    }

    public void registry(String key, Class<?> clazz, boolean isSingleton) {
        if (OBJECT_POOL.containsKey(key)) {
            return;
        }
        try {
            Constructor<?> constructor = clazz.getConstructor();
            Object bean = constructor.newInstance();
            if (isSingleton) {
                SINGLETON_SET.add(key);
                OBJECT_POOL.put(key, bean);
                OBJECT_POOL.put(clazz, bean);
            } else {
                OBJECT_POOL.put(key, constructor);
                OBJECT_POOL.put(clazz, constructor);
            }

        } catch (NoSuchMethodException
                  | InstantiationException
                  | InvocationTargetException
                  | IllegalAccessException ex) {
            throw new ReflectionsException();
        }
    }
}
