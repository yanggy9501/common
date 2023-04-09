package com.freeing.test.od;

import java.util.*;

/**
 * https://renjie.blog.csdn.net/article/details/128427589
 *
 * @author yanggy
 */
public class Od47 {
/*
bdni,wooood
bind,wrong,wood


 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] arr = scanner.nextLine().split(",");
        String[] answer = scanner.nextLine().split(",");
        System.out.println(guessWrod(arr, answer));
    }

    public static List<String>  guessWrod(String[] arr, String[] answer) {
        ArrayList<String> result = new ArrayList<>();
        for (String s1 : arr) {
            for (String s2 : answer) {
                // 统计或者排序
                char[] chars1 = s1.toCharArray();
                char[] chars2 = s2.toCharArray();
                if (s1.length() == s2.length()) {
                    Arrays.sort(chars1);
                    Arrays.sort(chars2);
                    if (Arrays.equals(chars1, chars2)) {
                        result.add(s2);
                    }
                    continue;
                }
                if (chars1.length < chars2.length) {
                    continue;
                }
                // 去重比较或者双指针
//                TreeSet<Character> set1 = new TreeSet<>();
//                for (char ch1 : chars1) {
//                    set1.add(ch1);
//                }
//                TreeSet<Character> set2 = new TreeSet<>();
//                for (char ch2 : chars2) {
//                    set2.add(ch2);
//                }
//                if (set1.size() == set2.size() && set1.containsAll(set2)) {
//                    result.add(s2);
//                }
                int i = 0, j = 0;
                while (i < chars1.length && j < chars2.length) {
                    // wooo o x d
                    // wooo d
                    if (chars1[i] == chars2[j]) {
                        i++;
                        j++;
                        continue;
                    }
                    // 调整
                    for (int k = i - 1;  k > 0 && chars1[k] == chars1[i]; i++) {}
                    if (chars1[i] != chars2[j]) {
                        break;
                    }
                    if (i == chars1.length - 1 && j == chars2.length - 1) {
                        result.add(s2);
                    }
                }
            }
        }
        return result;
    }
}
