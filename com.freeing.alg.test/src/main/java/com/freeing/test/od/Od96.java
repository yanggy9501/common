package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128601567
 *
 * @author yanggy
 */
public class Od96 {
    /*
AABBA

BBBBA

AAAAB
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        dp(s);
    }

    public static void dp(String str) {
        char[] chars = str.toCharArray();
        // dp数组：dp[i] 以chars[i] 表示 0 - i 的子串中最长的递增子串长度为 dp[i]
        int[] dp = new int[chars.length];
        // 初始化 dp[0] = 1 一个字符长度为1
        dp[0] = 1;
        // 递推公式 dp[i] = max(0到i - 1的dp[j], 或 dp[j] + 1)
        int max = 0;
        for (int i = 1; i < chars.length; i++) {
            for (int j = 0; j < i; j++) {
                if (chars[i] >= chars[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                } else {
                    dp[i] = 1;
                }
            }
            max = Math.max(dp[i], max);
        }
        System.out.println(Math.min(max, chars.length - max));
    }

    public static void dpTest(int[] array) {
        int length = array.length;
        int[] dp = new int[length];
        dp[0] = 1;
        int max = 0;
        for (int i = 1; i < length; i++) {
            if (array[i] > array[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = 1;
            }
            max = Math.max(max, dp[i]);
        }
    }
}
