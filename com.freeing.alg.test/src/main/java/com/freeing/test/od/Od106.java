package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od106 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int n = Integer.parseInt(s);
        int[] ints = new int[n];
        String[] split = scanner.nextLine().split(" ");
        int sum = 0;
        for (int i = 0; i < n; i++) {
            ints[i] = Integer.parseInt(split[i]);
            sum += ints[i];
        }
        int target = Integer.parseInt(scanner.nextLine());
        if (sum <= target) {
            System.out.println(-1);
            return;
        }
        int avg = sum / n;
        Arrays.sort(ints);
        for (int i = ints[n - 1] - 1; i >= avg; i--) {
            int finalI = i;
            int sum1 = Arrays.stream(ints).map(item -> Math.min(item, finalI)).sum();
            if (sum1 <= target) {
                System.out.println(i);
                break;
            }
        }
    }
}
