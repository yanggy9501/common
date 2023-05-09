package com.freeing.test.od;

import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od90 {
    /*
15
1 2
1 3
1 4
1 5
1 6
1 7
1 8
1 9
1 10
1 11
1 12
1 13
1 14
14 1
1 15
1

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int n = Integer.parseInt(s);
        String[] strings = new String[n];
        for (int i = 0; i < n; i++) {
            strings[i] = scanner.nextLine();
        }
        compute(strings, Integer.parseInt(scanner.nextLine()));
    }

    public static void compute(String[] array, int target) {
        // ints[i][*] 代表：i 发送的电信
        // ints[*][i] 代表：i 收到的短信
        int[][] ints = new int[101][101];
        for (String s : array) {
            String[] split = s.split(" ");
            int i1 = Integer.parseInt(split[0]);
            int i2 = Integer.parseInt(split[1]);
            ints[i1][i2] += 1;
        }
        // 判断1：target 发送短信接收在，没有给 target 发送短信的人的个数 L
        int l = 0;
        int f = 0, m = 0;
        for (int i = 1; i < 101; i++) {
            if (ints[target][i] > 0) {
                f += ints[target][i];
            }
            m += ints[i][target];
            if (ints[target][i] > 0 && ints[i][target] == 0) {
                l++;
            }
        }

        System.out.print(l > 5 || f - m > 10);
        System.out.print(" " + l + " " + (f - m));
    }
}
