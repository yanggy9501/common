package com.freeing.test.od;

import java.util.HashSet;
import java.util.Scanner;

/**
 *https://renjie.blog.csdn.net/article/details/128401727
 *
 * @author yanggy
 */
public class Od39 {
    /*
4 4
X X X X
X O O X
X O O X
X O X X

4 5
X X X X X
O O O O X
X O O O X
X O X X O

5 4
X O O O
X X X X
X O O O
X X X X
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int m = Integer.parseInt(split[0]);
        int n = Integer.parseInt(split[1]);
        String[][] array = new String[m][n];
        for (int i = 0; i < m; i++) {
            array[i] = scanner.nextLine().split(" ");
        }
        backTrackingOrdfs(array);
    }

    public static void backTrackingOrdfs(String[][] array) {
        int row = array.length;
        int col = array[0].length;
        HashSet<String> repeatSet = new HashSet<>();
        for (int i = 0; i < row; i++) {
            if (array[i][0].equals("O")) {
                repeatSet.add(i + ":0");
            }
            if (array[i][col - 1].equals("O")) {
                repeatSet.add(i + ":" + (col - 1));
            }
        }
        for (int i = 0; i < col; i++) {
            if (array[0][i].equals("O")) {
                repeatSet.add("0" + ":" + i);
            }
            if (array[row - 1][i].equals("O")) {
                repeatSet.add((row - 1) + ":" + i);
            }
        }
        HashSet<String> set;
        boolean[][] used = new boolean[row][col];
        String r = "NULL";
        for (String s : repeatSet) {
            String[] split = s.split(":");
            int row1 = Integer.parseInt(split[0]);
            int col1 = Integer.parseInt(split[1]);
            set = new HashSet<>(repeatSet);
            set.remove(s);
            if (!used[row1][col1] && doDfs(array, set, row1, col1, used)) {
                if (max < count) {
                    max = count;
                    r = row1 + " " + col1 + " " + max;
                }
                if (max == count) {
                    r = max + "";
                }
            }
            count = 0;
        }
        System.out.println(r);
    }

    public static int max = 0;
    public static int count = 0;
    public static boolean doDfs(String[][] array, HashSet<String> repeatSet, int row, int col, boolean[][] used) {
        used[row][col] = true;
        // 不触碰边界也不被使用
        if (repeatSet.contains(row + ":" + col)) {
            return false;
        }
        count++;
        // 下
        if (row + 1 < array.length && array[row + 1][col].equals("O") && !used[row + 1][col]) {
            return doDfs(array, repeatSet, row + 1, col, used);
        }

        // 上
        if (row - 1 > 0 && array[row - 1][col].equals("O") && !used[row - 1][col]) {
            return doDfs(array, repeatSet, row - 1, col, used);
        }

        // 右
        if (col + 1  < array[0].length && array[row][col + 1].equals("O") && !used[row][col + 1]) {
            return doDfs(array, repeatSet, row , col + 1, used);
        }

        // 左
        if (col - 1  > 0 && array[row][col - 1].equals("O") && !used[row][col - 1]) {
            return doDfs(array, repeatSet, row , col - 1, used);
        }
        // 其他都没有了返回最终的结果
        return true;
    }
}
