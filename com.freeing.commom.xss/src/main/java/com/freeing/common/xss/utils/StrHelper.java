package com.freeing.common.xss.utils;


/**
 * 字符串工具类
 *
 * @author yanggy
 */
public class StrHelper {
    /**
     * 字符串是否为空(null, 空)
     *
     * str 字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 字符串非null，非空串（空格字符串是可以的）
     *
     * str 字符串
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 字符串是否为空（null, 空，无空格，无制表符，无tab）
     *
     * str 字符串
     * @return boolean
     */
    public static boolean isBlank(String str) {
        if (str != null && (str.length() != 0)) {
            for (int i = 0; i < str.length(); i++) {
                // 判断字符串是否为空格，制表符，tab
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 字符串是否部位为空（null, 空，无空格，无制表符，无tab）
     *
     * str 字符串
     * @return boolean
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
