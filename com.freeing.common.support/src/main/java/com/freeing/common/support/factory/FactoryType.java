package com.freeing.common.support.factory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 工厂对象注解，被该注解修饰的类如果被工厂扫描，那么该等下就会被注册为一个单例
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FactoryType {
    /**
     * key
     */
    String value();

    /**
     * 所属工厂
     */
    Class<?> of();
}
