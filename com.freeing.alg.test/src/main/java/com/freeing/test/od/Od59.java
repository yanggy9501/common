package com.freeing.test.od;

import java.util.Objects;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128341540
 *
 * @author yanggy
 */
public class Od59 {
    /*
4 4
1 1 0 0
0 0 0 1
0 0 1 1
1 1 1 1

3 3
1 0 1
0 1 0
1 0 1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] ns = scanner.nextLine().split(" ");
        int m = Integer.parseInt(ns[0]);
        int n = Integer.parseInt(ns[1]);
        String[][] arr = new String[m][n];
        for (int i = 0; i < m; i++) {
            String[] split = scanner.nextLine().split(" ");
            for (int j = 0; j < split.length; j++) {
                arr[i][j] = split[j];
            }
        }

        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (Objects.equals(arr[i][j], "1")) {
                    bfs(arr, i, j);
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    public static void bfs(String[][] arr, int row, int col) {
        // 合法性校验
        if (row < 0 || row >= arr.length) {
            return;
        }
        if (col < 0 || col >= arr[0].length) {
            return;
        }
        if (Objects.equals(arr[row][col], "0")) {
            return;
        }
        arr[row][col] = "0";
        // 遍历八个方向
        bfs(arr, row - 1, col - 1);
        bfs(arr, row - 1, col);
        bfs(arr, row - 1, col + 1);

        bfs(arr, row + 1, col - 1);
        bfs(arr, row + 1, col);
        bfs(arr, row + 1, col + 1);

        bfs(arr, row, col - 1);
        bfs(arr, row, col + 1);
    }
}
