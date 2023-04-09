package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128436380
 *
 * @author yanggy
 */
public class Od50 {
    /*
hello123world1
hello123abc4

private_void_method
public_void_method
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();
        System.out.println(findCSubstring(s1, s2));

    }


    public static String findCSubstring(String str1, String str2) {
        // 定义db数组
        int[][] dp = new int[str1.length() + 1][str1.length() + 1];
        // 含义：dp[i][j] 分别以第 i - 1 和 j - 1 【结尾】时最长的公共长度
        // 初始化 dp[i][0] 和空字符比较，即 0
        // 递推公式 dp[i][j] if str1.charAt(i - 1) == str2.charAt(j - 1) dp[i][j] = dp[i - 1][j - 1] + 1;
        // 否则 0
        int max = 0;
        int x = 0 , y = 0;
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > max) {
                        max = dp[i][j];
                        x = i;
                        y = j;
                    }
                }
            }
        }
        System.out.println(max);
        return x > 0 ? str1.substring(x - max, x) : "";
    }
}
