package com.freeing.test.od;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od123 {
    /*
3 3
1 2 3
4 5 6
7 8 9

3 3
1 2 3
4 5 6
7 1 4
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int m = Integer.parseInt(split[0]);
        int n = Integer.parseInt(split[1]);
        int[][] array = new int[m][n];
        for (int i = 0; i < m; i++) {
            String[] split1 = scanner.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                array[i][j] = Integer.parseInt(split1[j]);
            }
        }
        prepare(array);
    }

    public static void prepare(int[][] array) {
        HashMap<Integer, List<Point>> repeatMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                int v = array[i][j];
                List<Point> compute = repeatMap.compute(v, (key, value) -> value == null ? new ArrayList<>() : value);
                compute.add(new Point(i, j));
            }
        }
        dfs(repeatMap, array, 0, 0, new boolean[array.length][array[0].length], 0);
        System.out.println(min);
    }

    static int[][] direction = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

    static int min = Integer.MAX_VALUE;

    public static void dfs(HashMap<Integer, List<Point>> repeatMap, int[][] array, int x, int y,
        boolean[][] visited, int cost) {
        if (visited[x][y]) {
            return;
        }
        if (x == array.length - 1 && y == array[0].length - 1) {
            min = Math.min(min, cost);
            visited[x][y] = true;
            return;
        }
        visited[x][y] = true;
        // 跳跃
        int curValue = array[x][y];
        List<Point> points = repeatMap.get(curValue);
        if (points.size() > 1) {
            for (Point point : points) {
                if (point.x == x && point.y == y) {
                    continue;
                }
                dfs(repeatMap, array, point.getX(), point.getY(), visited, cost);
            }
        }
        for (int[] direct0 : direction) {
            int nextX = x + direct0[0];
            int nextY = y + direct0[1];
            if (nextX < 0 || nextX >= array.length || nextY < 0 || nextY >= array[0].length) {
                continue;
            }
            dfs(repeatMap, array, nextX, nextY, visited, Math.abs(curValue - array[nextX][nextY]) + cost);
        }
        visited[x][y] = false;
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
