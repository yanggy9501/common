package com.freeing.test.od;

import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od127 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        boolean[] visited = new boolean[m + 10];
        backTracking(n, m, 0, visited);
        System.out.println(min);
    }

    public static void compute(int current, int target) {

    }

    public static int min = Integer.MAX_VALUE;
    public static void backTracking(int currentN, int targetM, int sum, boolean[] visited) {
        if (currentN == targetM) {
            min = Math.min(min, sum);
        }
        if (visited[currentN]) {
            return;
        }
        if (currentN <= 0) {
            return;
        }
        if (currentN > (targetM / 2 + 1) * 2) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                // // 向下
                backTracking(currentN - 1, targetM, sum + 1, visited);
            }
            if (i == 1) {
                // 向上
                backTracking(currentN + 1, targetM, sum + 1, visited);
            }
            if (i == 2) {
                // 向上
                backTracking(currentN * 2, targetM, sum + 1, visited);
            }
        }
    }
}
