package com.freeing.test.od;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od102 {
    /*
75 36 10 4 30 225 90 25 12

2 9 12 36 6 1 3 4 18
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Integer[] array = new Integer[9];
        String[] split = scanner.nextLine().split(" ");
        for (int i = 0; i < 9; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        backTracking(array, new boolean[9], new ArrayList<>(), 0);
        for (List<Integer> list : result) {
            System.out.println(list);
        }
    }

    public static List<List<Integer>> result = new ArrayList<>();

    public static void backTracking(Integer[] array, boolean[] visited, List<Integer> path, int count) {
        if (count ==  6) {
            int r1 = path.get(0) * path.get(1) * path.get(2);
            int r2 = path.get(3) * path.get(4) * path.get(5);
            if (r1 != r2) {
                return;
            }
        }
        if (count == 9) {
            int r1 = path.get(0) * path.get(1) * path.get(2);
            int r3 = path.get(6) * path.get(7) * path.get(8);
            if (r1 != r3) {
                return;
            }
            int c1 = path.get(0) * path.get(3) * path.get(6);
            int c2 = path.get(1) * path.get(4) * path.get(7);
            int c3 = path.get(2) * path.get(5) * path.get(8);
            if (c1 != c2 || c3 != c2) {
                return;
            }
            int cr1 = path.get(0) * path.get(4) * path.get(8);
            int cr2 = path.get(2) * path.get(4) * path.get(6);
            if (cr1 != cr2) {
                return;
            }
            result.add(new ArrayList<>(path));
        }

        for (int i = 0; i < array.length; i++) {
            if (visited[i]) {
                continue;
            }
            path.add(array[i]);
            visited[i] = true;
            backTracking(array, visited, path, count + 1);
            path.remove(path.size() - 1);
            visited[i] = false;
        }
    }
}
