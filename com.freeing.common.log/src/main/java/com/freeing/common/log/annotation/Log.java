package com.freeing.common.log.annotation;

import com.freeing.common.log.enums.BusinessType;

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
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作描述
     */
    String description() default "";

    /**
     * 是否保存请求参数
     */
    boolean saveParmas() default false;

    /**
     * 是否保存响应结果
     */
    boolean saveResult() default false;

    /**
     * 是否需要保存耗时
     */
    boolean saveConsumingTime() default true;
}
