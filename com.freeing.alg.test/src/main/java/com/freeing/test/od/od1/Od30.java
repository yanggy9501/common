package com.freeing.test.od.od1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/130921410
 *
 * @author yanggy
 */
public class Od30 {
    /*
2,10,-3,-8,40,5
4

8
1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(",");
        int k = Integer.parseInt(scanner.next());
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        func(array, k);
    }

    public static void func(int[] array, int k) {
        if (array.length < k) {
            throw new IllegalArgumentException();
        }
        int sum = Arrays.stream(array).limit(k).sum();
        int max = sum;
        for (int i = k; i < array.length; i++) {
            sum = sum - array[i - k] + array[i];
            max = Math.max(max, sum);
        }
        System.out.println(max);
    }
}
