package com.freeing.common.repsub.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 重复提交注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RepeatSubmit {
    /**
     * 间隔时间（ms），小于该时间间隔则视为重复提交
     * ps: 必须大于 1 s
     *
     * @return int
     */
    int interval() default 5000;

    /**
     * 时间单位
     *
     * @return TimeUnit
     */
    TimeUnit timeUnit() default TimeUnit.MICROSECONDS;

    /**
     * 重复添加时的提示消息，支持国际化，格式为 {code}
     *
     * @return message
     */
    String message() default "{repeat.submit.message}";
}
