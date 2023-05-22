package com.freeing.test.od.od1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/130756210
 *
 * @author yanggy
 */
public class Od01 {
    /*
3 3
YES YES NO
NO NO NO
NA NO YES

3 3
YES YES NO
NO NO NO
YES NO NO

4 4
YES NO NO NO
NO NO NO NO
NO NO NO NO
NO NO NO NO

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
        plan(array);
    }


    static int[][] directs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void plan(String[][] array) {
        Deque<Point> queue = new ArrayDeque<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                String s = array[i][j];
                if (Objects.equals("YES", s)) {
                    queue.addLast(new Point(i, j));
                }
                if (Objects.equals("NA", s)) {
                    System.out.println("-1");
                    return;
                }
            }
        }
        if (queue.isEmpty()) {
            return;
        }
        int count = -1;
        Point last = queue.getLast();
        while (!queue.isEmpty()) {
            Point point = queue.removeFirst();
            // 4 个方向
            for (int[] direct : directs) {
                int nx = point.x + direct[0];
                int ny = point.y + direct[1];
                if (nx < 0 || nx >= array.length || ny < 0 || ny >= array[0].length) {
                    continue;
                }
                if (array[nx][ny].equals("NO")) {
                    array[nx][ny] = "YES";
                    queue.addLast(new Point(nx, ny));
                }
            }
            if (point == last) {
                count++;
                if (!queue.isEmpty()) {
                    last = queue.getLast();
                }
            }
        }
        System.out.println(count);
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
