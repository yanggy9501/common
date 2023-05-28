package com.freeing.common.support.reflection.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yanggy
 */
public class AmbiguousMethodInvoker extends GetterSetterMethodInvoker {
    private final String exceptionMessage;

    public AmbiguousMethodInvoker(Method method, String exceptionMessage) {
        super(method);
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        throw new IllegalAccessException(exceptionMessage);
    }

}
