package com.freeing.common.component.utils;

import com.freeing.common.component.constants.StrPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 范围 - 工具类
 *
 * @author yanggy
 */
public class RangeUtils {
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
                    rangeBuilder.append(StrPool.HYPHEN).append(end);
                }
                rangeBuilder.append(StrPool.SEMICOLON);
                start = collect.get(i);
            }
            end = collect.get(i);
            if (i == collect.size() - 1) {
                rangeBuilder.append(start);
                if (start != end) {
                    rangeBuilder.append(StrPool.HYPHEN).append(end);
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
                if (!split.contains(StrPool.HYPHEN)) {
                    numbers.add(NumberUtils.parseInt(split));
                    continue;
                }
                String[] splitItems = split.split(StrPool.HYPHEN);
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

    /**
     * 判断 number 是否在 range 范围内
     * 
     * @param number int > 0
     * @param range 范围，如：1-4;6
     * @return boolean
     */
    public static boolean inRange(int number, String range) {
        if (StringUtils.isEmpty(range) || number < 0) {
            return false;
        }
        String[] splitArr = range.split(StrPool.SEMICOLON);
        for (String split : splitArr) {
            String[] itemArr = split.split(StrPool.HYPHEN);
            if (itemArr.length == 1 && itemArr[0].equals(String.valueOf(number))) {
                return true;
            }
            if (number >= NumberUtils.parseInt(itemArr[0], -1)
                && number <= NumberUtils.parseInt(itemArr[1], -1)) {
                return true;
            }
        }
        return false;
    }
}
