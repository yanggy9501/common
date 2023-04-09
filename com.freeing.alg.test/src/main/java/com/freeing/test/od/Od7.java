package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128025806
 *
 * @author yanggy
 */
public class Od7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        System.out.println(countOnCondition(start, end));
    }

    public static int countOnCondition(int start, int end) {
        int res = 0;
        for (int i = start; i <= end ; i++) {
            String binaryString = Integer.toBinaryString(i);
            if (binaryString.contains("101")) {
                res++;
            }
        }
        return (end - start + 1) - res;
    }
}
