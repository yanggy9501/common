package com.freeing.common.component.util;

import com.freeing.common.component.annotation.Trim;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author yanggy
 */
public class TrimUtils {

    /**
     * 为对象中有 @Trim 注解的 String 属性去两边空格
     * {@link Trim}
     *
     * @param ignoredFiled 忽略属性
     * @param object 对象
     */
    public static void trimByAnnotation(Object object, String... ignoredFiled) {
        if (object == null) {
            return;
        }
        HashSet<String> ignoredSet = new HashSet<>();
        if (ignoredFiled != null) {
            ignoredSet.addAll(Arrays.asList(ignoredFiled));
        }
        Class<?> clazz = object.getClass();
        while (clazz != null){
            boolean trimPresentType = clazz.isAnnotationPresent(Trim.class);
            Field[] fields = clazz.getDeclaredFields();
            char[] chars;
            String fieldName;
            String setMethodName;
            Method method;
            for (Field field : fields) {
                field.setAccessible(true);
                // 忽略 final 和 static 属性
                if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                // 类和属性都不存在 Trim 注解则跳过
                if (!field.isAnnotationPresent(Trim.class) && !trimPresentType) {
                    continue;
                }
                if (!ignoredSet.isEmpty() && ignoredSet.contains(field.getName())) {
                    continue;
                }
                try {
                    // 获取属性值并判断是否需要 trim
                    Object attrValue = field.get(object);
                    if (attrValue == null || attrValue.equals("")) {
                        continue;
                    }
                    if (attrValue instanceof String) {
                        fieldName = field.getName();
                        chars = fieldName.toCharArray();
                        chars[0] = (char) (chars[0] - 32);
                        // 为什么采用调用 setter 方法的方式而不是调用 Field#set：是为保证封装的完整性，setter 方法可以存在其他的操作
                        // 如：拼接前缀，后缀等等
                        setMethodName = "set" + String.valueOf(chars);
                        method = clazz.getMethod(setMethodName, String.class);
                        method.invoke(object, attrValue.toString().trim());
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    throw new UnsupportedOperationException(e);
                }
                field.setAccessible(false);
            }
            // 处理父类
            clazz = clazz.getSuperclass();
        }
    }
}
