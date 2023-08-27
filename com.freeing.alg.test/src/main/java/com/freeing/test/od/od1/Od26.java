package com.freeing.test.od.od1;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/130921622
 *
 * @author yanggy
 */
public class Od26 {
    /*
3
737270
737272
737288

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] files = new int[n];
        for (int i = 0; i < n; i++) {
            files[i] = scanner.nextInt();
        }
        dpPlan(files, 1474560);
    }

    public static void dpPlan(int[] files, int capacity) {
        int totalBlack = capacity >> 9;
        // dp 数组
        int[] dp = new int[files.length];
        int[] dpBlack = new int[files.length];
        // dp 数组含义，dp[i] 当有 0 - i 个物品时的最大容量为 totalBlack 时所能装的的最大容量
        // 递推公式：dp[i] = (dp[0] 到 dp[i - 1]) + files[i] 的最大值如果能装的下话
        // dp 初始化
        if (files[0] <= capacity ) {
            dp[0] = files[0];
            dpBlack[0] = (int) Math.ceil(files[0] / 512.0);
        }

        for (int i = 1; i < files.length; i++) {
            int size = files[i];
            int sizeBlack = (int) Math.ceil(size / 512.0);
            if (sizeBlack > totalBlack) {
                dp[i] = dp[i - 1];
                dpBlack[i] = dpBlack[i - 1];
            } else {
                int max = sizeBlack;
                int max2 = size;
                for (int j = 0; j < i; j++) {
                    // 能放下但不一定是最大的
                    int mayMax = sizeBlack + dpBlack[j];
                    if (mayMax <= totalBlack) {
                        if (mayMax > max) {
                            max = mayMax;
                            max2 = size + dp[j];
                        }
                    } else {
                        max = dpBlack[j];
                        max2 = dp[j];
                    }
                }
                // max 和
                dp[i] = max2;
                dpBlack[i] = max;

            }
        }
        System.out.println(dp[files.length - 1]);
    }
}
