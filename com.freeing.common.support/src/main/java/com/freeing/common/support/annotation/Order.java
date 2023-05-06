package com.freeing.common.support.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Order {

    int value() default Integer.MAX_VALUE;
}
