package com.freeing.common.component.constant;

import com.freeing.common.component.util.StringUtils;

/**
 * 升序降序
 */
public enum Direction {
    /**
     * 升序
     */
    ASC,

    /**
     * 降序
     */
    DESC;

    /**
     * 根据字符串值返回对应 Direction 值
     *
     * @param value 排序方式字符串，只能是 ASC或DESC
     * @return Direction，{@code null}表示提供的value为空
     * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value.
     */
    public static Direction fromString(String value) throws IllegalArgumentException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        // 兼容元数据中ASC和DESC表示
        if (1 == value.length()) {
            if ("A".equalsIgnoreCase(value)) {
                return ASC;
            }
            if ("D".equalsIgnoreCase(value)) {
                return DESC;
            }
        }
        try {
            return Direction.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid value [" + value + "] for orders given! Has to be either 'desc' or 'asc' (case insensitive).",  e);
        }
    }
}