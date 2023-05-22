package com.freeing.test.od.od1;

import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int[] ints = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            ints[i] = Integer.parseInt(split[i]);
        }
        compute(ints);
    }

    public static void compute(int[] array) {
        int[] prefixSumArray = new int[array.length];
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            prefixSumArray[i] = sum;
        }

        if (prefixSumArray[array.length - 1] == 0) {
            System.out.println(-1);
            return;
        }
        int index = -1;
        for (int i = 1; i < array.length - 1; i++) {
            int sum1 = prefixSumArray[i - 1];
            int sum2 = prefixSumArray[array.length - 1] - prefixSumArray[i];
            if (sum2 == sum1) {
                index = i;
                break;
            }
        }
        System.out.println(index);
    }
}
