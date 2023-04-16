package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128351616
 *
 * @author yanggy
 */
public class Od71 {
/*
6 5
0 0 0 -1 0
0 0 0 0 0
0 0 -1 4 0
0 0 0 0 0
0 0 0 0 -1
0 0 0 0 0
1 4
= 3

 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int start = 0, end = 0;
        int[][] arr = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = scanner.nextInt();
                if (arr[i][j] > 0) {
                    start = i;
                    end = j;
                }
            }
        }

        dfs(arr, start, end);
        int i = scanner.nextInt();
        int j = scanner.nextInt();
        System.out.println(arr[i][j]);
    }

    public static void dfs(int[][] mutrix, int x, int y) {
        dodfs(mutrix, x, y, new boolean[mutrix.length][mutrix[0].length]);
    }

    public static void dodfs(int[][] mutrix, int x, int y, boolean[][] visited) {
        if (visited[x][y]) {
            return;
        }
        visited[x][y] = true;
        // 上下左右
        if (x - 1 >= 0 && mutrix[x - 1][y] < mutrix[x][y] - 1 & mutrix[x - 1][y] >= 0) {
            mutrix[x - 1][y] = mutrix[x][y] - 1;
            dodfs(mutrix, x - 1, y, visited);
        }
        if (x + 1 < mutrix.length && mutrix[x + 1][y] < mutrix[x][y] - 1 && mutrix[x + 1][y] >= 0) {
            mutrix[x + 1][y] = mutrix[x][y] - 1;
            dodfs(mutrix, x + 1, y, visited);
        }
        if (y - 1 >= 0 && mutrix[x][y - 1] < mutrix[x][y] - 1 && mutrix[x][y - 1] >= 0) {
            mutrix[x][y - 1] = mutrix[x][y] - 1;
            dodfs(mutrix, x, y - 1, visited);
        }
        if (y + 1 < mutrix[0].length && mutrix[x][y + 1] < mutrix[x][y] - 1 &&  mutrix[x][y + 1] >= 0) {
            mutrix[x][y + 1] = mutrix[x][y] - 1;
            dodfs(mutrix, x, y + 1, visited);
        }
        // 回溯
        visited[x][y] = false;
    }
}
