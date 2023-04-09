package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128354622
 *
 * @author yanggy
 */
public class Od64 {
    /*
4
4 3 5 2

2
5 3
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        System.out.println(operate(arr));
    }
    public static int operate(int[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int a = arr[i] & arr[j];
                int b = arr[i] ^ arr[j];
                if (b > a) {
                    res++;
                }
            }
        }
        return res;
    }
}
