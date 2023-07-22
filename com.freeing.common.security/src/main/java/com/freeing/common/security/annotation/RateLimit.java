package com.freeing.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 防刷
 *
 * @author yanggy
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    /**
     * 默认每分钟限制访问 10 次
     *
     * @return 次数
     */
    int value() default 10;

    /**
     * 时间单位
     *
     * @return 单位
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
