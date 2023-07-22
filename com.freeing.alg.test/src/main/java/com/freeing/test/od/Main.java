package com.freeing.test.od;

import java.util.Scanner;

public class Main {
    /*
1474048 / 512 = 2879
513
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
        }

        //总容量
        int W = 1474560 / 512;

        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            int size = (int) Math.ceil(nums[i - 1] / 512.0);
            int value = nums[i - 1];
            for (int j = 0; j <= W; j++) {
                if (size > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - size] + value);
                }
            }
        }

        System.out.println(dp[n][W]);
    }
}