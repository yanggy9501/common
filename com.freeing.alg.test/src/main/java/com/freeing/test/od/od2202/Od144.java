package com.freeing.test.od.od2202;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128003990
 *
 * @author yanggy
 */
public class Od144 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int next = scanner.nextInt();
        int ln = scanner.nextInt();
        compute(next, ln);
    }

    public static void compute(int staffNumber, int letterNumber) {
        double pow = Math.pow(26, letterNumber);
        int letterValue = Double.valueOf(pow).intValue();
        int tmp = letterValue;
        int count = 0;
        while (tmp < staffNumber) {
            tmp *= 10;
            count++;
        }
        count = count == 0 ? 1 : count;
        System.out.println(count);
    }
}
