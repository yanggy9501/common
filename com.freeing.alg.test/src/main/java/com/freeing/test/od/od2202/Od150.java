package com.freeing.test.od.od2202;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128499649
 *
 * @author yanggy
 */
public class Od150 {
    /*
23,26,36,27
23,30,40

     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(",");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        int budget = Integer.parseInt(scanner.nextLine());
        backTracking(array, budget, 0, 0, 0);
        System.out.println(max);
    }

    static int max = -1;
    public static void backTracking(int[] priceArr, int budget, int buyCount, int sum, int startIndex) {
        if (buyCount > 3 || sum > budget) {
            return;
        }
        if (buyCount == 3) {
            max = Math.max(max, sum);
            return;
        }

        for (int i = startIndex; i < priceArr.length; i++) {
            sum += priceArr[i];
            backTracking(priceArr, budget, buyCount + 1, sum, i + 1);
            sum -= priceArr[i];
        }
    }
}
