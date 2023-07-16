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
     * 操作说明
     */
    String description() default "";

    /**
     * 项目模块
     */
    String module() default "";

    /**
     * 是否保存请求参数
     */
    boolean enableSaveParma() default true;

    /**
     * 是否保存响应结果
     */
    boolean enableSaveResult() default true;
}
