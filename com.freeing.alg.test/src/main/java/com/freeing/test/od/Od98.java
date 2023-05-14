package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128602061
 * 树的遍历
 *
 * @author yanggy
 */
public class Od98 {
    /*
3
5 0 0
1 5 0
0 1 5

     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int n = Integer.parseInt(s);
        Integer[][] array = new Integer[n][n];
        for (int i = 0; i < n; i++) {
            array[i] = Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
        }
        for (int i = 0; i < n; i++) {
            dfs(array, i, 0);
        }
        System.out.println(max);
    }

    public static int max = 0;
    public static void dfs(Integer[][] array, int row, int sum) {
        sum += array[row][row];
        max = Math.max(max, sum);
        for (int i = 0; i < array[row].length; i++) {
            if (array[row][i].equals(0) || row == i) {
                continue;
            }
            dfs(array, i, sum);
        }
    }
}
