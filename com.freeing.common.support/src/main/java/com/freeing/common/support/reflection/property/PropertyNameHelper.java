package com.freeing.common.support.reflection.property;

import java.util.Locale;

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
        String finalName;
        if (name.startsWith("is")) {
            finalName = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
            finalName = name.substring(3);
        } else {
            throw new IllegalArgumentException("Error parsing property name '" + name +
                "'.  Didn't start with 'is', 'get' or 'set'.");
        }
        if (finalName.length() == 1 || (finalName.length() > 1 && !Character.isUpperCase(finalName.charAt(1)))) {
            finalName = finalName.substring(0, 1).toLowerCase(Locale.ENGLISH) + finalName.substring(1);
        }
        return finalName;
    }

    public static boolean isGetter(String name) {
        return (name.startsWith("get") && name.length() > 3) || (name.startsWith("is") && name.length() > 2);
    }

    public static boolean isSetter(String name) {
        return (name.startsWith("set") && name.length() > 3);
    }
}
