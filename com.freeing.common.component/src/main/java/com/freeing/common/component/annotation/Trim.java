package com.freeing.common.component.annotation;

import java.lang.annotation.*;

/**
 * 类的 String 属性去两边空格标记
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Trim {

}
