package com.freeing.test.od;

import java.util.HashSet;
import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 * 1: 回溯算法
 *
 * @author yanggy
 */
public class Od77 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i1 = scanner.nextInt();
        int i2 = scanner.nextInt();
        int number = scanner.nextInt();
        int[] ints = new int[2];
        ints[0] = i1;
        ints[1] = i2;
        System.out.println(findAllCombine(ints, number));
    }

    public static HashSet<Integer> findAllCombine(int[] arr, int number) {
        backTracking(arr, 0, 0, 0, number);
        return result;
    }

    public static HashSet<Integer> result = new HashSet<>();
    public static void backTracking(int[] arr, int startIndex, int sum, int count, int number) {
        if (number == 0 || count > number) {
            return;
        }
        if (count == number) {
            result.add(sum);
            return;
        }
        for (int i = startIndex; i < arr.length; i++) {
            sum += arr[i];
            count++;
            backTracking(arr, i, sum, count, number);
            count--;
            sum -= arr[i];
        }
    }
}
