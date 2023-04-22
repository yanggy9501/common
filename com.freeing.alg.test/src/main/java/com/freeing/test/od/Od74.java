package com.freeing.test.od;

import java.util.HashMap;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128402626
 * 重复元素的删除：从头到尾判断删除的（高位在前），当前能否删除取决于下一个元素是比当前大还是小
 * 大：可删除
 * 小：不删除
 *
 * @author yanggy
 */
public class Od74 {
    /*
34533

5445795045
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(getMaxValueAfterDel(s));
    }

    public static String getMaxValueAfterDel(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : chars) {
            Integer i = map.compute(ch, (k, v) -> v == null ? 0 : v);
            map.put(ch, i + 1);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            Character ch = chars[i];
            Integer n = map.get(ch);
            if (n > 2) {
                if (i == chars.length - 1) {
                    break;
                }
                if (ch <= chars[i + 1]) {
                    Integer k = map.get(ch);
                    map.put(ch, k - 1);
                    continue;
                }
            }
            builder.append(ch);
        }
        return builder.toString();
    }
}
