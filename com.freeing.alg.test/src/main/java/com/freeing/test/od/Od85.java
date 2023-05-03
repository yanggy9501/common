package com.freeing.test.od;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128571548
 * 滑动窗口
 *
 * @author yanggy
 */
public class Od85 {
    /*
qweebaewqd
qwe

abab
ab

aaaaabbbbbbbbba
ab
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String content = scanner.next();
        String word = scanner.next();
        compute(content, word);
    }

    public static void compute(String content, String word) {
        // 一个“窗口”的字符统计
        HashMap<Character, Integer> contentWindowCountMap = new HashMap<>();
        HashMap<Character, Integer> wordCountMap = new HashMap<>();

        for (char ch : word.toCharArray()) {
            wordCountMap.compute(ch, (k, v) -> v == null ? 1 : ++v);
        }

        int wordSize = word.length();
        int matchKind = 0;
        int result = 0;
        int allMatchKind = wordCountMap.size();
        for (int j = 0; j < content.length(); j++) {
            Character ch = content.charAt(j);
            if (j >= wordSize) {
                // 窗口的开始位置
                int start = j - wordSize;
                char startCh = content.charAt(start);
                if (wordCountMap.containsKey(startCh)
                    && Objects.equals(contentWindowCountMap.get(startCh), wordCountMap.get(startCh))) {
                    matchKind--;
                }
                // contentWindowCountMap.compute(startCh, (k, v) -> v == null ? 0 : --v);
                contentWindowCountMap.compute(startCh, (k, v) -> --v);
            }
            contentWindowCountMap.compute(ch, (k, v) -> v == null ? 1 : ++v);
            if (wordCountMap.containsKey(ch) && Objects.equals(wordCountMap.get(ch), contentWindowCountMap.get(ch))) {
                matchKind++;
            }
            if (matchKind == allMatchKind) {
                result++;
            }
        }
        System.out.println(result);
    }
}
