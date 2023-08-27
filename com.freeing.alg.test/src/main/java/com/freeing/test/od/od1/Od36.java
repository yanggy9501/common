package com.freeing.test.od.od1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/131007459
 *
 * @author yanggy
 */
public class Od36 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int[] array = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
        System.out.println(Math.max(dp(array, 0, array.length - 2), dp(array, 1, array.length - 1)));
    }

    public static int dp(int[] nums, int start, int end) {
        return 0;
    }
}
