package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128585413
 *
 * @author yanggy
 */
public class Od82 {
    /*
0 1 0

1 1 0 1 2 1 0
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        compute(split);
    }

    public static void compute(String[] strArr) {
        // dp 数组：dp[i] 第 i 位位置左边的连续 1 的个数
        int[] dp = new int[strArr.length];
        int[] dp2 = new int[strArr.length];
        // init
        // dp[0] = 0;
        for (int i = 1; i < strArr.length; i++) {
            if (strArr[i - 1].equals("1")) {
                dp[i] = dp[i - 1] + 1;
            }
        }

        for (int i = strArr.length - 2; i >= 0; i--) {
            if (strArr[i + 1].equals("1")) {
                dp2[i] = dp2[i + 1] + 1;
            }
        }
        int max = 0;
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals("0")) {
                max = Math.max(max, dp[i] + dp2[i]);
            }
        }
        System.out.println(max);
    }
}
