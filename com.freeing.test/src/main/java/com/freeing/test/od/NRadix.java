package com.freeing.test.od;

import java.util.Scanner;

/**
 * N 进制减法, 加法
 *
 * @author yanggy
 */
public class NRadix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] arr = line.split(" ");
            String subtract = subtract1(arr[1], arr[2], Integer.parseInt(arr[0]));
            System.out.println(subtract);
        }

    }

    public static String subtract1(String subtracted, String minuend, int radix) {
        // 合法性校验
        if (radix < 2 || radix > 35) {
            return "-1";
        }
        if (minuend.length() != 1 && minuend.startsWith("0")) {
            return "-1";
        }
        if (subtracted.length() != 1 && subtracted.startsWith("0")) {
            return "-1";
        }

        StringBuilder result = new StringBuilder();

        int first = Integer.parseInt(subtracted, radix);
        int second = Integer.parseInt(minuend, radix);

        int res = first - second;
        if (res > 0) {
            return "0" + " " + Integer.toString(res, radix);
        } else {
            return "1" + " " + Integer.toString(res, radix);
        }
    }
}
