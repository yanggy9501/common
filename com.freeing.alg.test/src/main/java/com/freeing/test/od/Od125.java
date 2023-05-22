package com.freeing.test.od;

import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od125 {
    /*
4 4 10
10 20 30 40
100 120 140 160
200 230 260 290
300 400 500 600
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int m = Integer.parseInt(split[0]);
        int n = Integer.parseInt(split[1]);
        int heightDifference = Integer.parseInt(split[2]);
        int[][] array = new int[m][n];
        for (int i = 0; i < m; i++) {
            String[] split1 = scanner.nextLine().split(" ");
            for (int j = 0; j < split1.length; j++) {
                array[i][j] = Integer.parseInt(split1[j]);
            }
        }
        dfs(array, new boolean[m][n], 0, 0, 0, 0, heightDifference);
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }

    static int[][] directions = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    static int min = Integer.MAX_VALUE;
    public static void dfs (int[][] array, boolean[][] visited, int row, int col, int pathSize, int count, int heightDifference) {
        if (row == array.length - 1 && col == array[0].length - 1) {
            min = Math.min(min, pathSize);
        }
        if (visited[row][col]) {
            return;
        }
        visited[row][col] = true;
        for (int[] direct : directions) {
            int nextRow = row + direct[0];
            int nextCol = col + direct[1];
            if (nextRow < 0 || nextRow >= array.length || nextCol < 0 || nextCol >= array[0].length) {
                continue;
            }
            if (Math.abs(array[row][col] - array[nextRow][nextCol]) <= heightDifference) {
                dfs(array, visited, nextRow, nextCol, pathSize + 1, count, heightDifference);
            } else if (count <= 3) {
                dfs(array, visited, nextRow, nextCol, pathSize + 1, count + 1, heightDifference);
            } else {
                continue;
            }
        }
        visited[row][col] = false;
    }
}
