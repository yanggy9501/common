package com.freeing.test.od;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128402456
 *
 * @author yanggy
 */
public class Od43 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int step = Integer.parseInt(s);
        String[] split = scanner.nextLine().split(",");
        int[] array = new int[split.length];
        int count = 0;
        for (String s1 : split) {
            array[count++] = Integer.parseInt(s1);
        }
        findBad(array, step);
    }

    public static void findBad(int[] array, int step) {
        ArrayList<Integer> list = new ArrayList<>();
        Deque<Integer> queue = new LinkedList<>();
        int min;
        for (int i = 0; i < array.length; i++) {
            queue.addLast(array[i]);
            if (i < step - 1) {
                if (i == array.length -  1) {
                    min = queue.element();
                    for (int num : queue) {
                        min = Math.min(num, min);
                    }
                    break;
                }
                continue;
            }
            // 找最小值，这里还可以优化
            min = queue.element();
            for (int num : queue) {
                min = Math.min(num, min);
            }
            list.add(min);
            queue.removeFirst();
        }
        System.out.println(list);
    }
}
