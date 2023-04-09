package com.freeing.test.od;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128318301
 * 由于是数组实现的了可以直接找到父节点
 *
 * @author yanggy
 */
public class Od55 {
    /*
3 5 7 -1 -1 2 4

5 9 8 -1 -1 7 -1 -1 -1 -1 -1 6
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int[] ints = new int[split.length + 1];
        for (int i = 0; i < split.length; i++) {
            ints[i + 1] = Integer.parseInt(split[i]);
        }
        System.out.println(Arrays.toString(path(ints)));
    }

    public static Integer[] path(int[] tree) {
        if (tree.length == 2) {
            return Arrays.asList(tree[1]).toArray(new Integer[0]);
        }
        int min = tree[2];
        int index = 2;
        for (int i = 2; i < tree.length; i++) {
            if (tree[i] < min && tree[i] != -1) {
                min = tree[i];
                index = i;
            }
        }
        LinkedList<Integer> path = new LinkedList<>();
        while (index > 0) {
            path.addFirst(tree[index]);
            index /=  2;
        }
        return path.toArray(new Integer[0]);
    }
}
