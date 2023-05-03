package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128573000
 * 动态滑动窗口
 *
 * @author yanggy
 */
public class Od84 {
    /*
1000
100 300 500 400 400 150 100


     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int target = Integer.parseInt(s);
        String[] split = scanner.nextLine().split(" ");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        compute(array, target);
    }

    public static void compute(int array[], int target) {
        int windowSum = 0;
        int right = 0;
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            windowSum += array[i];
            // 加上当前位 < target 的情况
            if (windowSum <= target) {
                max = Math.max(max, windowSum);
            }
            // 加上当前位 > target 的情况
            while (windowSum >= target) {
                if (windowSum <= target) {
                    max = Math.max(max, windowSum);
                    break;
                }
                windowSum -= array[right++];
            }
        }
        System.out.println(max);
    }
}
