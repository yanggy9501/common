package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128570481
 *
 * @author yanggy
 */
public class Od86 {
    /*
4
50 20 20 60
90

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        int[] array = new int[i];
        for (int j = 0; j < i; j++) {
            array[j] = scanner.nextInt();
        }
        int target = scanner.nextInt();
        computedp2(array, target);
    }

    public static void computedp1(int[] array, int target) {
        // dp[i][j] 当有 i 个设备时，上限为 j 时的最大功率值
        int[][] dp = new int[array.length][target + 1];
        // init
        for (int i = array[0]; i <= target; i++) {
            dp[0][i] = array[0];
        }
        // 递推公式 dp[i][j] = dp[i - 1][j - array[i]] + array[j] 或  dp[i - 1][j]

        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j <= target; j++) {
                if (j < array[i]) {
                    // 当前物品无法装下
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 能装下取最大值
                    dp[i][j] = Math.max(dp[i - 1][j - array[i]] + array[i], dp[i - 1][j]);
                }
            }
        }
        System.out.println(dp[array.length - 1][target]);
    }

    public static void computedp2(int[] array, int target) {
        // dp[i] 当有 i - 1 个设备时，上限为 target 时的最大功率值
        int[] dp = new int[array.length + 1];
        if (target >= array[0]) {
            dp[1] = array[0];
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i] > target) {
                dp[i + 1] = dp[i];
            } else {
                int max = 0;
                for (int j = 0; j <= i; j++) {
                    if (dp[j] + array[i] <= target) {
                        max = Math.max(max, dp[j] + array[i]);
                    }
                }
                dp[i + 1] = Math.max(max, dp[i]);
            }
        }
        System.out.println(dp[array.length]);
    }
}
