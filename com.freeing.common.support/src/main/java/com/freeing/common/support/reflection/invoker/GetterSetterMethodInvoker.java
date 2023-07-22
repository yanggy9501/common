package com.freeing.common.support.reflection.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法执行器
 *
 * @author yanggy
 */
public class GetterSetterMethodInvoker implements Invoker {
    /**
     * getter 方法的返回值类型或者 setter 方法的参数类型
     */
    private Class<?> type;

    private Method method;

    public GetterSetterMethodInvoker(Method method) {
        this.method = method;
        if (method.getParameterTypes().length > 1) {
            throw new IllegalArgumentException("The method " + method.toGenericString()
                + " is not standard setter method.");
        }
        if (method.getParameterTypes().length == 1) {
            type = method.getParameterTypes()[0];
        } else {
            type = method.getReturnType();
        }
    }

    @Override
    public Object invoke(Object target, Object... args) throws IllegalAccessException, InvocationTargetException {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException e) {
            method.setAccessible(true);
            return method.invoke(target, args);
        }
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
