package com.freeing.common.log.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 操作日志使用spring event异步入库
 *
 * @author yanggy
 */
@Aspect
public class LogAspect {
    /**
     * 排除敏感属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword",
        "username", "phone", "mobilePhone"};

    /***
     * 定义切面
     */
    @Pointcut("@annotation(com.freeing.common.log.annotation.Log)")
    public void logPointcut() {

    }
}