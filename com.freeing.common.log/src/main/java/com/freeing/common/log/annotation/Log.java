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
    BusinessType bizType() default BusinessType.OTHER;

    /**
     * 操作说明
     */
    String desc() default "";

    /**
     * 是否保存请求参数
     */
    boolean isSaveP() default true;

    /**
     * 是否保存响应结果
     */
    boolean isSaveR() default true;
}
