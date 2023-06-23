package com.freeing.common.support.factory;

import java.lang.annotation.*;

/**
 * 工厂对象注解，被该注解修饰的类如果被工厂扫描，那么该等下就会被注册为一个单例
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface FactoryType {
    /**
     * key
     */
    String value() default "";

    /**
     * 所属工厂
     */
    Class<?> of();

    /**
     * 是否单例
     */
    boolean singleton() default true;
}
