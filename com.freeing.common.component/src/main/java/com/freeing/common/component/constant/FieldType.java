package com.freeing.common.component.constant;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author yanggy
 */
public enum FieldType {
    /* string */
    STRING, STRING_LIST, STRING_MAP,
    /* boolean */
    BOOLEAN, BOOLEAN_LIST, BOOLEAN_MAP,
    /* integer */
    INTEGER, INTEGER_LIST, INTEGER_MAP,
    /* DOUBLE */
    DOUBLE, DOUBLE_LIST, DOUBLE_MAP,
    /* LONG */
    LONG, LONG_LIST, LONG_MAP,
    /* FLOAT */
    FLOAT, FLOAT_LIST, FLOAT_MAP,
    /* DATE */
    DATE, DATE_LIST, DATE_MAP,
    /* BYTE */
    BYTE_ARRAY,
    /* ENUM */
    ENUM,
    ;

    public static FieldType getFieldType(Field field) throws Exception {
        // 基本类型
        if (field.getType().isAssignableFrom(String.class)) {
            return FieldType.STRING;
        }
        if (field.getType().isAssignableFrom(Integer.class)) {
            return FieldType.INTEGER;
        }
        if (field.getType().isAssignableFrom(Double.class)) {
            return FieldType.DOUBLE;
        }
        if (field.getType().isAssignableFrom(Long.class)) {
            return FieldType.LONG;
        }
        if (field.getType().isAssignableFrom(Float.class)) {
            return FieldType.FLOAT;
        }
        if (field.getType().isAssignableFrom(Date.class)) {
            return FieldType.DATE;
        }
        if (field.getType().isAssignableFrom(Boolean.class)) {
            return FieldType.BOOLEAN;
        }
        if (field.getType().isAssignableFrom(byte[].class)) {
            return FieldType.BYTE_ARRAY;
        }

        // 数组类型
        if (List.class.isAssignableFrom(field.getType())) {
            ParameterizedType parameterized = (ParameterizedType) field.getGenericType();
            Class<?> actualClass = (Class<?>) parameterized.getActualTypeArguments()[0];
            if (String.class.isAssignableFrom(actualClass)) {
                return FieldType.STRING_LIST;
            }
            if (Integer.class.isAssignableFrom(actualClass)) {
                return FieldType.INTEGER_LIST;
            }
            if (Double.class.isAssignableFrom(actualClass)) {
                return FieldType.DOUBLE_LIST;
            }
            if (Long.class.isAssignableFrom(actualClass)) {
                return FieldType.LONG_LIST;
            }
            if (Float.class.isAssignableFrom(actualClass)) {
                return FieldType.FLOAT_LIST;
            }
            if (Date.class.isAssignableFrom(actualClass)) {
                return FieldType.DATE_LIST;
            }
            if (Boolean.class.isAssignableFrom(actualClass)) {
                return FieldType.BOOLEAN_LIST;
            }
        }

        // map 类型
        if (Map.class.isAssignableFrom(field.getType())) {
            ParameterizedType parameterized = (ParameterizedType) field.getGenericType();
            Class<?> actualClass = (Class<?>) parameterized.getActualTypeArguments()[1];
            if (String.class.isAssignableFrom(actualClass)) {
                return FieldType.STRING_MAP;
            }
            if (Integer.class.isAssignableFrom(actualClass)) {
                return FieldType.INTEGER_MAP;
            }
            if (Double.class.isAssignableFrom(actualClass)) {
                return FieldType.DOUBLE_MAP;
            }
            if (Long.class.isAssignableFrom(actualClass)) {
                return FieldType.LONG_LIST;
            }
            if (Float.class.isAssignableFrom(actualClass)) {
                return FieldType.FLOAT_MAP;
            }
            if (Date.class.isAssignableFrom(actualClass)) {
                return FieldType.DATE_MAP;
            }
            if (Boolean.class.isAssignableFrom(actualClass)) {
                return FieldType.BOOLEAN_MAP;
            }
        }
        if (field.getType().isEnum()) {
            return FieldType.ENUM;
        }
        throw new Exception("unknown filed type{name:" + field.getName() + "}");
    }
}
