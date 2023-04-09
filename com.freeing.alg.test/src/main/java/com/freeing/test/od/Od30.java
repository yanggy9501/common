package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128259675
 *
 * @author yanggy
 */
public class Od30 {
    public static void main(String[] args) {
        /*
6
10 20 30 40 60
=70

15
10 20 30 40 60 60 70 80 90 150
=210
         */
        Scanner scanner = new Scanner(System.in);
        int budget = Integer.parseInt(scanner.nextLine());
        String[] splits = scanner.nextLine().split(" ");
        int[] messageArr = new int[splits.length];
        int i = 0;
        for (String s : splits) {
            messageArr[i++] = Integer.parseInt(s);
        }
        System.out.println(compute(budget, messageArr));
    }

    // 最优问题，并且这个是规模的问题，并且有关系，可以初步判断是不是动态规划
    // 受到预算和条数规模两个维度影响，先判断（有 i 条短信，预算 j）的最优情况
    // 此时的最优情况：
    // 1. 上一次（有 i - 1 条短信，预算 j）与（有 i - 1 条短信，预算 j - x）的情况
    public static int compute(int budget, int[] messageArr) {
        // 定义dp 数组; dp[i][j]: 有 i 个短信优惠系列，并且预算为 j 时可获得的最优值
        int[][] dp = new int[messageArr.length][budget + 1];

        // 递推公式：dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - i] + messageArr[i])

        // dp 初始化
        for (int j = 1; j <= budget; j++) {
            dp[0][j] = dp[0][j - 1] + messageArr[0];
        }

        // 动态规划
        for (int i = 1; i < messageArr.length; i++) {
            for (int j = 1; j <= budget; j++) {
                if (j - i > 0) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - i - 1] + messageArr[i]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[messageArr.length - 1][budget];
    }
}
