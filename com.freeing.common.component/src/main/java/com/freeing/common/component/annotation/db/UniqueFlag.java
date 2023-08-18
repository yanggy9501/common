package com.freeing.common.component.annotation.db;

import java.lang.annotation.*;

/**
 * 注解：标识唯一性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface UniqueFlag {

}
