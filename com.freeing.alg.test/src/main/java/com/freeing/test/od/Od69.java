package com.freeing.test.od;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od69 {
    /*
6
10 1 2
-21 3 4
23 5
14
35
66
1 1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int n = Integer.parseInt(s);
        String[] arr = new String[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextLine();
        }
        String[] split = scanner.nextLine().split(" ");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        bfs(arr, x, y);
    }

    public static void bfs(String[] strArr, int x, int y) {
        LinkedList<String> queue = new LinkedList<>();
        if (x == 0 && y == 0) {
            System.out.println(strArr[0].split(" ")[0]);
        }
        queue.addLast(strArr[0]);
        int x1 = 0, y1 =0;
        String levelPoint = strArr[0];
        while (!queue.isEmpty()) {
            String item = queue.removeFirst();
            String[] split = item.split(" ");
            if (split.length == 2) {
                if (item == levelPoint) {
                    x1++;
                    levelPoint = strArr[Integer.parseInt(split[1])];
                }
                queue.addLast(strArr[Integer.parseInt(split[1])]);
            }
            if (split.length == 3) {
                if (item == levelPoint) {
                    x1++;
                    levelPoint = strArr[Integer.parseInt(split[2])];
                }
                queue.addLast(strArr[Integer.parseInt(split[1])]);
                queue.addLast(strArr[Integer.parseInt(split[2])]);
            }
            if (x1 == x) {
                break;
            }
        }
        for (int i = 0; i < queue.size(); i++) {
            if (i == y) {
                String s = queue.get(i);
                System.out.println(s.split(" ")[0]);
                break;
            }
        }
    }
}
