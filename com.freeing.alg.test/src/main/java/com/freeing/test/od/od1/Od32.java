package com.freeing.test.od.od1;

import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/130957621
 *
 * @author yanggy
 */
public class Od32 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = scanner.nextInt();
            }
            func(m, array);
        }
    }

public static void func(int target, int[] array) {
    int sum = 0;
    for (int i = 0; i < array.length; i++) {
        int value = array[i];
        sum += value;
        if (sum % target == 0) {
            System.out.println(1);
            return;
        } else {
            int tmp = sum;
            for (int j = 0; j < i; j++) {
                tmp -= array[j];
                if (tmp < target) {
                    break;
                }
                if (tmp % target == 0) {
                    System.out.println(1);
                    return;
                }
            }
        }
    }
    System.out.println(0);
}
}
