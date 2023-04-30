package com.freeing.common.security.annotation;

import com.freeing.common.security.enumnew.EncryptStrategy;

import java.lang.annotation.*;

/**
 * 加密注解
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Encrypt {
    /**
     * 加密策略
     *
     * @return
     */
    EncryptStrategy strategy();
}
