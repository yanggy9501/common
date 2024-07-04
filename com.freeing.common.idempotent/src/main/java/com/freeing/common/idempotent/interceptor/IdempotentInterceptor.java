package com.freeing.common.idempotent.interceptor;

import com.freeing.common.idempotent.IdempotentChecker;
import com.freeing.common.idempotent.annotation.Idempotent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.lang.reflect.Method;

public class IdempotentInterceptor extends HandlerInterceptorAdapter {

    private IdempotentChecker idempotentChecker;

    public IdempotentInterceptor(IdempotentChecker idempotentChecker) {
        this.idempotentChecker = idempotentChecker;
    }

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
        javax.servlet.http.HttpServletResponse response,
        Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        Idempotent annotation = method.getAnnotation(Idempotent.class);
        if (annotation != null) {
            idempotentChecker.checkToken(request);
        }
        return true;
    }
}
