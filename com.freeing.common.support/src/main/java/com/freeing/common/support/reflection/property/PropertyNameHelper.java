package com.freeing.common.support.reflection.property;

/**
 * @author yanggy
 */
public class PropertyNameHelper {

    /**
     * getter | setter 方法名转换为属性命名
     *
     * @param name method name
     * @return property name
     */
    public static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            return name.substring(2);
        }
        if (name.startsWith("get") || name.startsWith("set")) {
            return name.substring(3);
        }
        throw new IllegalArgumentException("Error parsing property name '" + name +
            "'.  Didn't start with 'is', 'get' or 'set'.");
    }

    public static boolean isGetter(String name) {
        return (name.startsWith("get") && name.length() > 3) || (name.startsWith("is") && name.length() > 2);
    }
}
