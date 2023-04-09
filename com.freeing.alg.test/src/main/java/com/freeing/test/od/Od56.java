package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128319496
 *
 * @author yanggy
 */
public class Od56 {
    /*
4 4
1 2 5 2
2 4 4 5
3 5 7 1
4 6 2 4

2 3
1 3 5
4 1 3
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int m = Integer.parseInt(split[0]);
        int n = Integer.parseInt(split[1]);
        int[][] arr = new int[m][n];
        for (int i = 0; i < m; i++) {
            String[] mtri = scanner.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(mtri[j]);
            }
        }
        System.out.println(findlongestPath(arr));
    }

    public static int findlongestPath(int[][] arr) {
        boolean[][] visited = new boolean[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                currentValue = 0;
                dfs(i, j, arr, visited);
            }
        }
        return logestValue;
    }

    static int logestValue = 0;
    static int currentValue = 0;
    public static void dfs(int row, int col, int[][] arr , boolean[][] visited) {
        if (visited[row][col]) {
            return;
        }
        // 访问
        visited[row][col] = true;
        logestValue = Math.max(logestValue, ++currentValue);
        // 上
        if (row - 1 >= 0
            && Math.abs(arr[row][col] - arr[row - 1][col]) <= 1) {
            dfs(row - 1, col, arr, visited);
        }
        // 下
        if (row + 1 < arr.length
            && Math.abs(arr[row][col] - arr[row + 1][col]) <= 1) {
            dfs(row + 1, col, arr, visited);
        }
        // 左
        if (col - 1 >= 0
            && Math.abs(arr[row][col] - arr[row][col - 1]) <= 1) {
            dfs(row, col - 1, arr, visited);
        }
        // 右
        if (col + 1 < arr[0].length
            && Math.abs(arr[row][col] - arr[row][col + 1]) <= 1) {
            dfs(row, col + 1, arr, visited);
        }
    }
}
