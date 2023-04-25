package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128590610
 *
 * @author yanggy
 */
public class Od79 {
/*
5 7
5 7 9 15 10

 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i1 = scanner.nextInt();
        int i2 = scanner.nextInt();
        int[] array = new int[i1];
        for (int i = 0; i < i1; i++) {
            array[i] = scanner.nextInt();
        }
        System.out.println(min(array, i2));
    }


    /**
     * 本质就是平摊
     *
     * @param array
     * @param days
     * @return
     */
    public static int min(int[] array, int days) {
        if (days < array.length) {
            return -1;
        }
        Arrays.sort(array);
        int min = Arrays.stream(array).sum() / days;
        int max = array[array.length - 1];

        int[] infos = new int[array.length];
        for (int take = min; take <= max; take++) {
            // 做 days 天
            int rest = 0;
            int count = 0;
            // 一块田完了，才能开始下一个
            for (int i = 0; i < array.length && count <= days;) {
                count++;
                rest = take + rest - array[i];
                if (rest >= 0) {
                    i++;
                }
                while (rest < 0) {
                    rest = take - rest;
                    count++;
                }
            }
            if (count <= days) {
                return take;
            }
        }
        return -1;
    }
}
