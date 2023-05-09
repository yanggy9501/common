package com.freeing.test.od;

import java.util.Objects;
import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od87 {
    /*
2 0
5 5
..S..
****.
T....
****.
.....

1 2
5 5
.*S*.
*****
..*..
*****
T....


2 0
3 3
S..
**.
T..

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int tc = Integer.parseInt(split[0]);
        int c = Integer.parseInt(split[1]);
        split = scanner.nextLine().split(" ");
        int m = Integer.parseInt(split[0]);
        int n = Integer.parseInt(split[1]);
        String[][] map = new String[m][n];

        int row = 0;
        int col = 0;
        for (int i = 0; i < m; i++) {
            char[] chars = scanner.nextLine().toCharArray();
            int j = 0;
            for (char ch : chars) {
                map[i][j] = String.valueOf(ch);
                if (ch == 'S') {
                    row = i;
                    col = j;
                }
                j++;
            }
        }
        dfs(map, row, col, tc, c);
    }

    public static void dfs(String[][] map, int row, int col, int tc, int c) {
        // 注意第一次进入方向是不确定的，方向 固定 0 ，进入默认转弯一次，所以转弯次数计数从 -1 开始
        doDfs(map, new boolean[map.length][map[0].length], row, col, -1, 0 , tc, c, 0);
        System.out.println(result);
    }

    private static String result = "NO";
    public static void doDfs(String[][] map, boolean[][] used, int row, int col, int myTc, int myC, int tc, int c, int direct) {
        if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) {
            return;
        }
        // 转向用完
        if (myTc > tc) {
            return;
        }
        // 破壁用完
        if (myC > c) {
            return;
        }
        // 当前位置已经访问
        if (used[row][col]) {
            return;
        }
        // 目标
        if (Objects.equals(map[row][col], "T")) {
            result = "YES";
            return;
        }
        // 当前需要破壁
        if (Objects.equals(map[row][col], "*") ) {
            ++myC;
        }
        used[row][col] = true;
        // 上 12
        if (direct == 12) {
            doDfs(map, used, row - 1, col, myTc, myC, tc, c, direct);
        } else {
            // 转弯
            doDfs(map, used, row - 1, col, myTc + 1, myC, tc, c, 34);
        }

        // 下 12
        if (direct == 12) {
            doDfs(map, used, row + 1, col, myTc, myC, tc, c, direct);
        } else {
            // 转弯
            doDfs(map, used, row + 1, col, myTc + 1, myC, tc, c, 34);
        }

        // 左 34
        if (direct == 34) {
            doDfs(map, used, row , col - 1, myTc, myC, tc, c, direct);
        } else {
            // 转弯
            doDfs(map, used, row , col - 1, myTc + 1, myC, tc, c, 13);
        }

        // 右 34
        if (direct == 34) {
            doDfs(map, used, row , col +  1, myTc, myC, tc, c, direct);
        } else {
            // 转弯
            doDfs(map, used, row , col + 1, myTc + 1, myC, tc, c, 34);
        }
        used[row][col] = false;
    }
}
