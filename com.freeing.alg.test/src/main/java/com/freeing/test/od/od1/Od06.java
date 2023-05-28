package com.freeing.test.od.od1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od06 {
/*
5,4,2,3,2,4,9
10
 */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(",");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        int target = Integer.parseInt(scanner.nextLine());
        // backTracking(array, target, 0, 0);
        // System.out.println(result);
        dynamicPlan(array, target);
    }

    public static void compute(int[] array) {

    }

    static List<Integer> path = new ArrayList<>();
    static List<List<Integer>> result = new ArrayList<>();
    public static void backTracking(int[] array, int target, int sum, int startIndex) {
        if (sum > target) {
            return;
        }
        if (sum == target) {
            result.add(new ArrayList<>(path));
        }
        for (int i = startIndex; i < array.length; i++) {
            sum += array[i];
            path.add(array[i]);;
            backTracking(array, target, sum, i + 1);
            sum -= array[i];
            path.remove(path.size() - 1);
        }
    }

    public static void dynamicPlan(int[] array, int target) {
        // dp[i][j] 有 0 - i 个数能够凑成 j 的这一个数的个数为 dp[i][j]
        int[][] dp = new int[array.length][target + 1];
        // 递推公式 dp[i][j]，当到第 i 个数时，和为 j 的情况：
        // 1：不考虑第 i 个数的情况 dp[i + 1][j]
        // 2：考虑第 i 个数的情况 dp[i][j - array[j]]，j - array[j] >= 0 才考虑
        // 两种情况相加

        // 初始化
        for (int i = 0; i < array.length; i++) {
            // 这一步很重要，这一步是基础，任何一个  dp[i][j - array[j]] 其中 j - array[j] = 0，都应该是 1
            dp[i][0] = 1;
        }
        dp[0][array[0]] = 1;
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j < target + 1; j++) {
                if (j - array[i] >= 0) {
                    dp[i][j] = dp[i - 1][j - array[i]] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        System.out.println(dp[array.length - 1][target]);
    }
}
