package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128266061
 *
 * @author yanggy
 */
public class Od31 {
    public static void main(String[] args) {
        /*
5 3
4 5 3 5 5

5 2
4 5 3 5 5
         */
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split(" ");
        int count = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);

        int[] arr = new int[count];
        String[] s1 = scanner.nextLine().split(" ");
        for (int i = 0; i < s1.length; i++) {
            arr[i] = Integer.parseInt(s1[i]);
        }
        plan(m, arr);
    }

    public static void plan(int m, int[] arr) {
        int result = 0;
        while (m > 0) {
            Arrays.sort(arr);
            int sub = 0;
            int index = 0;
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] - arr[i - 1] > 0) {
                    sub = arr[i] - arr[i - 1];
                    index = i - 1;
                    break;
                }
            }
            if (sub == 0 ) {
                result = arr[0] + m / arr.length;
                break;
            } else {
                if (sub * (index + 1) > m) {
                    result = arr[0];
                    break;
                } else {
                    for (int i = 0; i <= index; i++) {
                        arr[i] += sub;
                        m -= sub;
                    }
                    if (m == 0) {
                        result = arr[0];
                    }
                }
            }
        }
        System.out.println(result);
    }
}
