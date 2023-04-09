package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128331812
 *
 * @author yanggy
 */
public class Od57 {
    /*
40 3
20 10
20 20
20 5

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int total = Integer.parseInt(split[0]);
        int n = Integer.parseInt(split[1]);
        Task[] tasks = new Task[n];
        for (int i = 0; i < n; i++) {
            String[] arr = scanner.nextLine().split(" ");
            Task task = new Task();
            task.take = Integer.parseInt(arr[0]);
            task.reward = Integer.parseInt(arr[1]);
            tasks[i] = task;
        }
        System.out.println(bestReturn(tasks, total));
    }

    public static int bestReturn(Task[] tasks, int total) {
        // dp[i][j] 当有第 i 个物品，总工时为 j 时的最大回报时 dp[i][j]
        int[][] dp = new int[tasks.length][total + 1];
        // 递推公式：dp[i][j] = 当前任务可执行 dp[i][j] = max(dp[i - 1][j], dp[i][j - task.take] 当不能执行时保持 dp[i - 1][j]
        // 初始化
        for (int i = tasks[0].take; i <= total; i++) {
            dp[0][i] = tasks[0].reward;
        }

        for (int i = 1; i < tasks.length; i++) {
            Task task = tasks[i];
            for (int j = 0; j <= total; j++) {
                if (task.take <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - task.take] + task.reward);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[tasks.length - 1][total];
    }

    public static class Task{
        int take;

        int reward;
    }
}
