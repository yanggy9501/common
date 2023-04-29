package com.freeing.common.component.utils;

import com.freeing.common.component.annotation.Trim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author yanggy
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

    private static final Logger log = LoggerFactory.getLogger(ObjectUtils.class);

    /**
     * 给对象中有 @Trim 注解的属性去两边空格
     * {@link Trim}
     *
     * @param object 对象
     */
    public static void trim(Object object) {
        if (object == null) {
            return;
        }
        Class<?> clazz = object.getClass();
        boolean TrimPresentType = clazz.isAnnotationPresent(Trim.class);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // 忽略 final 和 static 属性
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            // 类和属性都不存在 Trim 注解则跳过
            if (!field.isAnnotationPresent(Trim.class) && !TrimPresentType) {
                continue;
            }
            try {
                // 获取属性值并判断是否需要 trim
                Object attrValue = field.get(object);
                if (attrValue == null || attrValue.equals("")) {
                    continue;
                }
                if (attrValue instanceof String) {
                    String fieldName = field.getName();
                    char[] chars = fieldName.toCharArray();
                    chars[0] = (char) (chars[0] - 32);
                    String setMethodName = "set" + String.valueOf(chars);
                    Method method = clazz.getMethod(setMethodName, String.class);
                    method.invoke(object, attrValue.toString().trim());
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                log.warn("LOG00060: class: {}", object.getClass().getTypeName(), e);
            }
            field.setAccessible(false);
        }
    }
}
