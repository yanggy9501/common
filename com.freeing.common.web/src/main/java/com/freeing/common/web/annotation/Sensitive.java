package com.freeing.common.web.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.freeing.common.web.jsonserializer.SensitiveJsonSerializer;
import com.freeing.common.web.enumnew.SensitiveStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 序列化脱敏，指定自定义的序列化器进行脱敏
 * ps：只适用 String 类型
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializer.class)
public @interface Sensitive {
    /**
     * 脱敏策略
     *
     * @return
     */
    SensitiveStrategy strategy();
}
