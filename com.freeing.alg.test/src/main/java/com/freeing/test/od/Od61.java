package com.freeing.test.od;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128347013
 *
 * @author yanggy
 */
public class Od61 {
/*
3 3
1 2 3

14 7
2 3 2 5 5 1 4
 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = scanner.nextInt();
        int n = scanner.nextInt();
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = scanner.nextInt();
        }
        func(sum, ints);
    }

    public static void func(int sum, int[] arr) {
        int arrSum = Arrays.stream(arr).sum();
        if (arrSum <= sum) {
            System.out.println(Arrays.toString(new int[0]));
        }
        int avg = arrSum / arr.length;
        OptionalInt max = Arrays.stream(arr).max();
        int tagert = 0;
        for (int i = avg; i < max.getAsInt(); i++) {
            int sum1 = 0;
            for (int n : arr) {
                sum1 += Math.min(n, avg);
            }
            if (sum1 > sum) {
                tagert = avg - 1;
                break;
            }
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > tagert) {
                arr[i] = arr[i] - tagert;
            } else {
                arr[i] = 0;
            }

        }
        System.out.println(Arrays.toString(arr));
    }
}
