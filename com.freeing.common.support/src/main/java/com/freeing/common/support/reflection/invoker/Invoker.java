package com.freeing.common.support.reflection.invoker;

import java.lang.reflect.InvocationTargetException;

/**
 * 方法执行器
 */
public interface Invoker {

    Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException;

    Class<?> getType();
}
