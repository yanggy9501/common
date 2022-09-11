package com.freeing.common.component.utils;

import com.freeing.common.component.constant.StrPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符串
     */
    private static final String EMPTY_STR = "";

    /**
     * 下划线
     */
    private static final char UNDERLINE = '_';

    /**
     * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return EMPTY_STR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY_STR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     * input: str="aaa.bbb", endStr='.'
     * output: "aaa"
     *
     * @param str    字符串
     * @param endChar 结束字符
     * @return 结果
     */
    public static String substring(final String str, char endChar) {
        return substring(str, 0, str.indexOf(endChar));
    }

    /**
     * 截取符合正则表达式的字符串
     *
     * @param str 字符串
     * @param regex 正则表达式
     * @return 结果
     */
    public static String substring(final String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return EMPTY_STR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY_STR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 驼峰转下划线命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase;
        // 当前字符是否大写
        boolean curreCharIsUpperCase;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(UNDERLINE);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains(String.valueOf(UNDERLINE))) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法 例如：user_name -> userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == UNDERLINE) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 数字左边补齐0，使之达到指定长度。注意，如果数字转换为字符串后，长度大于size，则只保留 最后size个字符。
     *
     * @param num  数字对象
     * @param size 字符串指定长度
     * @return 返回数字的字符串格式，该字符串为指定长度。
     */
    public static String padLeft(final Number num, final int size) {
        return padLeft(num.toString(), size, '0');
    }

    /**
     * 字符串左补齐。如果原始字符串s长度大于size，则只保留最后size个字符。
     *
     * @param s    原始字符串
     * @param size 字符串指定长度
     * @param c    用于补齐的字符
     * @return 返回指定长度的字符串，由原字符串左补齐或截取得到。
     */
    public static String padLeft(final String s, final int size, final char c) {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                for (int i = size - len; i > 0; i--) {
                    sb.append(c);
                }
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            for (int i = size; i > 0; i--) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取整数的范围
     * 输入：[1, 2, 2, 3, 4, 6, 8, 9, null]
     * 输出：1-4;6;8-9
     *
     * @param numbers 输入，集中的每一项不能为null
     * @return String，如 1-4;6;8-9
     */
    public static String segment(List<Integer> numbers) {
        // 剔除 null + 去重
        List<Integer> collect = numbers.stream()
            .filter(Objects::nonNull)
            .distinct()
            .sorted(Integer::compareTo)
            .collect(Collectors.toList());

        // 全null
        if (collect.size() == 0) {
            return "";
        }
        // 只有一个
        if (collect.size() == 1) {
            return collect.get(0).toString();
        }

        StringBuilder rangeBuilder = new StringBuilder();
        int start = collect.get(0);
        int end = start;
        for (int i = 1; i < collect.size(); i++) {
            // 不连续时拼接 start-end;
            if (!collect.get(i).equals(collect.get(i - 1) + 1)) {
                rangeBuilder.append(start);
                if (start != end) {
                    rangeBuilder.append("-").append(end);
                }
                rangeBuilder.append(";");
                start = collect.get(i);
            }
            end = collect.get(i);
            if (i == collect.size() - 1) {
                rangeBuilder.append(start);
                if (start != end) {
                    rangeBuilder.append("-").append(end);
                }
            }
        }
        return rangeBuilder.toString();
    }

    /**
     * 合并范围
     * 输入
     * 1-4;6
     * 2-5;7
     * 8;10
     * 输出：1-8;10
     *
     * @param ranges 范围字符数组
     * @return String
     */
    public static String mergeSegment(String...ranges) {
        if (ranges == null || ranges.length == 0) {
            return StrPool.EMPTY;
        }
        ArrayList<Integer> numbers = new ArrayList<>();
        for (String range : ranges) {
            if (StringUtils.isEmpty(range)) {
                continue;
            }
            String[] splitArr = range.split(StrPool.SEMICOLON);
            for (String split : splitArr) {
                if (!split.contains(StrPool.DASH)) {
                    numbers.add(NumberUtils.parseInt(split));
                    continue;
                }
                String[] splitItems = split.split(StrPool.DASH);
                int start = NumberUtils.parseInt(splitItems[0]);
                int end = NumberUtils.parseInt(splitItems[1]);
                if (start >= end) {
                    throw new IllegalArgumentException("Illegal range argument: " + split);
                }
                for (;start <= end; start++) {
                    numbers.add(start);
                }
            }
        }
        return segment(numbers);
    }
}
