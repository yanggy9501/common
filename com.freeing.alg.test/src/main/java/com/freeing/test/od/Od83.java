package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128115383
 * 滑动窗口
 *
 * @author yanggy
 */
public class Od83 {
    /*
2 5 2 6
1 3 4 5 8
2 3 6 7 1

5 2 2 6
1 2
3 3
4 6
5 7
8 1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int m = Integer.parseInt(split[0]);
        int n = Integer.parseInt(split[1]);
        int c = Integer.parseInt(split[2]);
        int k = Integer.parseInt(split[3]);
        int[][] array = new int[m][n];
        for (int i = 0; i < m; i++) {
            String[] split1 = scanner.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                array[i][j] = Integer.parseInt(split1[j]);
            }
        }
        compute(array, c, k);
    }

    public static void compute(int[][] array, int n, int target) {
        int count = 0;
        for (int i = n - 1; i < array.length; i++) {
            int sum = sumOf(array, n, i, n - 1);
            if (sum >= target) {
                count++;
            }
            for (int j = n; j < array[0].length; j++) {
                sum -= sumOfCol(array, n, i, j - 1);
                sum += sumOfCol(array, n, i, j);
                if (sum >= target) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    public static int sumOf(int[][] array, int n, int x, int y) {
        int currentSum = 0;
        for (int i = x - n + 1; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                currentSum += array[i][j];
            }
        }
        return currentSum;
    }

    public static int sumOfCol(int[][] array, int n, int row, int col) {
        int currentSum = 0;
        for (int j = row - n + 1; j <= row; j++) {
            currentSum += array[j][col];
        }
        return currentSum;
    }
}
