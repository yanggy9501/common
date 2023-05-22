package com.freeing.test.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/129930543
 *
 * @author yanggy
 */
public class Od129 {
/*
3
8 1 9
3 5 7
4 6 2

 */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int[][] array = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] split = scanner.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                array[i][j] = Integer.parseInt(split[j]);
            }
        }
        func(array, n);
    }

    public static void func(int[][] array, int n) {
        // 给一个坐标能得出该行该列的和为固定值 N*(N^2 + 1) /2 ，若交换两个数字，将会有4个坐标的计算不为该值，两坐标构成的矩形对角
        // 因为都用到了交换过的两个坐标
        // 这个值可以自己去统计
        int target = n * (n * n + 1) / 2;

        int[][] tags = new int[n][n];
        ArrayList<Point> points = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tags[row][col] != target) {
                    int sum = Arrays.stream(array[row]).sum();
                    if (sum == target) {
                        for (int i = 0; i < n; i++) {
                            tags[row][i] = target;
                        }
                    }
                    int sum2 = 0;
                    for (int i = 0; i < n; i++) {
                        sum2 += array[i][col];
                    }
                    if (sum == target) {
                        for (int i = 0; i < n; i++) {
                            tags[i][col] = target;
                        }
                    }
                    if (tags[row][col] != target && sum2 == sum) {
                        points.add(new Point(row, col));
                    }
                }
            }
        }
        Point point1 = points.get(0);
        Point point2 = points.get(1);
        point2.value = array[point1.row][point1.col];
        point1.value = array[point2.row][point2.col];
        System.out.println(point1);
        System.out.println(point2);
    }


    public static class Point {
        public int row;

        public int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int value;

        @Override
        public String toString() {
            return
                "" + (row + 1) +
                " " + (col + 1) +
                " " + value;
        }
    }
}
