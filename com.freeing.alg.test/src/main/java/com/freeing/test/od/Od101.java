package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128719333
 * dfs
 *
 * @author yanggy
 */
public class Od101 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int i = Integer.parseInt(s);
        String[][] array = new String[i][i];
        for (int j = 0; j < i; j++) {
            array[j] = scanner.nextLine().split(" ");
        }
        for (int j = 0; j < i; j++) {
            dfs(array, new boolean[i], j, 0);
        }
        System.out.println(max);
    }

    public static int max = 0;

    public static void dfs(String[][] array, boolean[] visited, int depend, int sum) {
        if (visited[depend]) {
            return;
        }
        max = Math.max(max, ++sum);
        visited[depend] = true;

        for (int i = 0; i < array[depend].length; i++) {
            if (i == depend) {
                continue;
            }
            if (array[depend][i].equals("1")) {
                dfs(array, visited, i, sum);
            }
        }
    }
}
