package com.freeing.common.component.utils;

import com.freeing.common.component.constants.StrPool;
import com.freeing.common.component.utils.stack.CharStack;
import com.freeing.common.component.utils.stack.IntStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Number工具类
 *
 * @author yanggy
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
    /**
     * 判断两个整型是否相等
     *
     * @param number1 整数1
     * @param number2 整数2
     * @return boolean
     */
    public static <T extends Number> boolean equals(T number1, T number2) {
        if (number1 == null && number2 == null) {
            return true;
        }
        return number1 != null && number1.equals(number2);
    }

    /**
     * 判断两个整型是否相等
     *
     * @param number 整数
     * @param defaultValue 默认值
     * @return boolean
     */
    public static <T extends Number> T getOfDefualt(T number, T defaultValue) {
        if (number != null) {
            return number;
        }
        return defaultValue;
    }

    /**
     * 解析字符串，默认值为 0
     *
     * @param number int类型字符串
     * @return int
     */
    public static int parseInt(String number) {
       return parseInt(number, 0);
    }

    /**
     * 解析字符串，忽略异常默认值为 0
     *
     * @param number int类型字符串
     * @param defaultValue 默认字符串
     * @return int
     */
    public static int parseInt(String number, int defaultValue) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException ignore) {
            return defaultValue;
        }
    }

    /**
     * 解析字符串，忽略异常默认值为 0L
     *
     * @param number int类型字符串
     * @return long
     */
    public static long parseLong(String number) {
        return parseLong(number, 0L);
    }

    /**
     * 解析字符串
     *
     * @param number long类型字符串
     * @param defaultValue 默认字符串
     * @return long
     */
    public static long parseLong(String number, long defaultValue) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException ignore) {
            return defaultValue;
        }
    }

    /**
     * 计算两个N进制无符号整数之和
     *
     * @param numStr1 N进制整型
     * @param numStr2 N进制整型
     * @param radix 基数
     * @return 相加和
     */
    public static String add(String numStr1, String numStr2, int radix) {
        IntStack stackA = prepareAddData(numStr1.toCharArray(), radix);
        IntStack stackB = prepareAddData(numStr2.toCharArray(), radix);
        CharStack resStack = new CharStack(Math.max(numStr1.length(), numStr2.length()) + 1);

        int carry = 0;
        int a;
        int b;
        while (!stackA.isEmpty() || !stackB.isEmpty() || carry != 0) {
            a = stackA.isEmpty() ? 0 : stackA.pop();
            b = stackB.isEmpty() ? 0 : stackB.pop() ;
            int res = (a + b + carry) % radix;
            carry = (a + b + carry) / radix;
            resStack.push(Character.forDigit(res, radix));
        }

        StringBuilder builder = new StringBuilder();
        while (!resStack.isEmpty()) {
            builder.append(resStack.pop());
        }
        return builder.toString();
    }

    private static IntStack prepareAddData(char[] chars, int radix) {
        IntStack stack = new IntStack(chars.length);
        int res;
        for (char ch : chars) {
            if ('0' <= ch && ch<= '9') {
                res = ch - 48 < radix ? ch - 48 : 0;
            } else if ('a' <= ch && ch <= 'z'){
                res = ch - 97 < radix ? ch - 87 : 0;
            } else if ('A' <= ch && ch <= 'Z') {
                res = ch - 65 < radix ? ch - 55 : 0;
            } else {
                res = 0;
            }
            stack.push(res);
        }
        return stack;
    }

    /**
     * 找出数组中均值小于等于一指定值的最大区间
     * ps：可能存在多个
     *
     * @param array 数组
     * @param value 均值
     * @return 数组下标对
     */
    public static String avgRangeLenght(int[] array, int value) {
        int len = array.length;
        int sum;
        ArrayList<String> result = new ArrayList<>();
        result.add(String.valueOf(array[0]));
        int currentMax = 1;
        int j = 1;
        for (int i = 0; i < len; i++) {
            for (; j < len; j++) {
                sum = sum(array, i, j);
                // 均值 < value，在判断长度是否有更大的或者等于最大
                // 1. 如果有更大的，清空之前的结果，保存新的结果，更新最大值
                // 2. 等于之前的，保存结果
                int subLen = j - i + 1;
                // 这里用乘法，避免使用除法出现浮点数
                if (sum <= value * subLen) {
                    if (subLen == currentMax) {
                        result.add(i == j ? String.valueOf(j) : i + "-" + j);
                    }
                    if (subLen > currentMax) {
                        result.clear();
                        result.add(i + "-" + j);
                        currentMax++;
                    }
                } else {
                    // 当超过了 break 保持 i 与 j 的距离活动（相当滑动窗口）
                    break;
                }
            }
        }
        return String.join(StrPool.SEMICOLON, result);
    }

    /**
     * 计算数组指定闭区间的和
     *
     * @param array 数组
     * @param start 起始下标
     * @param end 结束小标
     * @return 和
     */
    public static int sum(int[] array, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += array[i];
        }
        return sum;
    }

    /**
     * 查找数组中的众数
     *
     * @param array 数组
     * @return 众数
     */
    public static List<Integer> findMode(int[] array) {
        Arrays.sort(array);

        int count = 1;
        int currentMax = 0;
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i - 1]) {
                count++;
            } else {
                if (count == currentMax) {
                    result.add(array[i - 1]);
                }
                if (count > currentMax) {
                    // 有新的众数
                    result.clear();
                    result.add(array[i - 1]);
                    count = 1;
                }
            }
        }
        return result;
    }
}
