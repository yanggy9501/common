package com.freeing.common.component.annotation;

import java.lang.annotation.*;

/**
 * 注解：com.freeing.common.component.util.TrimUtils 对对象的 String 类型的属性进行去重
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Trim {

}
