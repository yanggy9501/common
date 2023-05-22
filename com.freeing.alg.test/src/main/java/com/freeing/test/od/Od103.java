package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od103 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int[] kindTotal = new int[3];
        for (int i = 0; i < 3; i++) {
            kindTotal[i] = Integer.parseInt(split[i]);
        }
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            int price = Integer.parseInt(scanner.nextLine());
            int[] ints = Arrays.copyOf(kindTotal, kindTotal.length);
            res = Integer.MAX_VALUE;
            total = 0;
            backTracking(price, kindTotal, ints, 0);
            System.out.println(res + " " + total);
        }
    }

    public static int res = Integer.MAX_VALUE;
    public static int total = 0;
    public static void backTracking(int curPrice, int[] kindTotal, int[] curkindTotal, int used) {
        int count = 0;
        for (int i = 0; i < kindTotal.length; i++) {
            if (kindTotal[i] != curkindTotal[i]) {
                count++;
            }
        }
        if (count > 2) {
            return;
        } else {
            if (curPrice < res) {
                res = curPrice;
                total = used;
            }
        }

        for (int i = 0; i < curkindTotal.length; i++) {
            int n = curkindTotal[i];
            if (n <= 0 || kindTotal[i] != curkindTotal[i]) {
                continue;
            }
            int tmpPrice = curPrice;
            int tmpUsed = used;
            if (i == 0 && curPrice >= 100) {
                used += Math.min(curPrice / 100, kindTotal[i]);
                int dis = (curPrice / 100 * 10) * Math.min(curPrice / 100, kindTotal[i]);
                curPrice -= dis;
            } else if (i == 1) {
                used--;
                curPrice = (int)(curPrice * 0.92);
            } else if (i == 2) {
                used += kindTotal[i];
                curPrice = curPrice - 5 * kindTotal[i];
            } else {
                continue;
            }
            // 记录变化就行，因为是连续使用的
            curkindTotal[i] = --n;
            backTracking(curPrice, kindTotal, curkindTotal, used);
            curkindTotal[i] = ++n;
            used = tmpUsed;
            curPrice = tmpPrice;
        }
    }
}
