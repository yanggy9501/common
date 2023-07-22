package com.freeing.common.support.reflection.invoker;

import java.lang.reflect.InvocationTargetException;

/**
 * 方法执行器
 */
public interface Invoker {

    /**
     *
     * @param target 目标对象
     * @param args 目标对象 Method 的入参，若没有参数则入参为 null
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    Object invoke(Object target, Object... args) throws IllegalAccessException, InvocationTargetException;

    Class<?> getType();
}
