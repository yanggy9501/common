package com.freeing.common.component.annotation;

import java.lang.annotation.*;

/**
 * 持久化对象相关校验
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CheckPersist {
    /**
     * 是否允许为 null
     */
    boolean allowNull() default true;

    /**
     * 是否允许为空
     */
    boolean allowEmpty() default true;
}
