package com.freeing.test.od.od1;

import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od10 {

    /*
22222
00000
00000
11111

22220
00020
00010
01111
#
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] matrix = new int[300][300];
        int index = 0;
        while (true) {
            String s = scanner.nextLine();
            if (s.equals("#")) {
                break;
            }
            for (int i = 0; i < s.length(); i++) {
                matrix[index][i] = Integer.parseInt(String.valueOf(s.charAt(i)));
            }
            index++;
        }
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                if (matrix[i][j] != 0) {
                    sum = 0;
                    dfs(matrix, i, j);
                }
            }
        }
        System.out.println(max);
    }

    static int max = 0;
    static int sum = 0;
    static int[][] directs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public static void dfs(int[][] array, int row, int col) {
        if (array[row][col] == 0) {
            return;
        }
        sum += array[row][col];
        max = Math.max(sum, max);
        array[row][col] = 0;

        // 遍历4个方向
        for (int[] direct : directs) {
            int nextRow = direct[0] + row;
            int nextCol = direct[1] + col;
            if (nextRow < 0 || nextCol < 0 || nextRow >= array.length || nextCol >= array[0].length) {
                continue;
            }
            dfs(array, nextRow, nextCol);
        }
    }
}
