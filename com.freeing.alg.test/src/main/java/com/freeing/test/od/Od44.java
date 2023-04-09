package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128418102
 * 图的变量：dfs + 联通图
 *
 * @author yanggy
 */
public class Od44 {
/*
4
1 1 1 1
1 1 1 0
1 1 1 0
1 0 0 1


4
1 1 0 0
1 1 0 0
0 0 1 0
0 0 0 1
 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[][] arr = new String[n][n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextLine().split(" ");
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }
            dfs(i, arr, visited);
            count++;
        }
        System.out.println(count);
    }

    public static void dfs(int i, String[][] grap, boolean[] visited) {
       if (visited[i]) {
           return;
       }
        for (int j = i; j < grap.length; j++) {
            if (grap[i][j].equals("1")) {
                visited[j] = true;
                dfs(j, grap, visited);
            }
        }
    }
}
