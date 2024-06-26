package com.freeing.common.log.annotation;

import com.freeing.common.log.enums.BizType;

import java.lang.annotation.*;

/**
 * 日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 操作类型
     */
    BizType bizType() default BizType.OTHER;

    /**
     * 操作说明
     */
    String description() default "";

    /**
     * 是否保存请求参数
     */
    boolean isSaveIn() default true;

    /**
     * 是否保存响应结果
     */
    boolean isSaveOut() default true;
}
