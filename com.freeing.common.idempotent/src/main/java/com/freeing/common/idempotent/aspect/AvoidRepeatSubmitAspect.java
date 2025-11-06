package com.freeing.common.idempotent.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class AvoidRepeatSubmitAspect {
    /***
     * 定义切面
     */
    @Pointcut("@annotation(com.freeing.common.idempotent.annotation.AvoidRepeatSubmit)")
    public void avoidRepeatSubmitAspect() {

    }

    @Around(value = "avoidRepeatSubmitAspect()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = getRequest();
        String requestURI = request.getRequestURI();

        Object[] args = pjp.getArgs();

        // 注意集群环境-需要分布式锁
        return null;
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getRequest();
        }
        return null;
    }
}
