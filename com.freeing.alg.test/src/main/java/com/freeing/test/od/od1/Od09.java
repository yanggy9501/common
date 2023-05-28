package com.freeing.test.od.od1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od09 {
    /*
3
14
10 4 5

3
14
10 5 4

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cnt = Integer.parseInt(scanner.nextLine());
        int initValue = Integer.parseInt(scanner.nextLine());
        int[] array = new int[cnt];
        String[] split = scanner.nextLine().split(" ");
        for (int i = 0; i < cnt; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        compute(array, initValue);
        compute2(array, initValue);
    }

    public static void compute(int[] array, int initValue) {
        // 速度
        int v = 0;
        int reminder = initValue - array[0];
        if (reminder < 0) {
            return;
        }
        for (int i = 1; i < array.length; i++) {
            int value = array[i];
            // 上一次剩下的 + 单位时间增加的 - 本次消耗的
            int tmpReminder = reminder + v - value;
            // 小于 0 则速度需要调整
            if (tmpReminder < 0) {
                // v 动态调整，将 tmpReminder 平摊到每一个单位时间上
                int carry = (-tmpReminder) / i;
                // 浮点数 -> 整数存在调整，如 1.5 -> 2
                carry = carry * i < (-tmpReminder) ? carry + 1 : carry;
                v += carry;
                reminder = tmpReminder + carry * i;
            } else {
                reminder = tmpReminder;
            }
        }
        System.out.println(v);
    }

    public static void compute2(int[] array, int initValue) {
        int sum = Arrays.stream(array).sum();
        int v = (sum - initValue) / array.length;
        if (v < 0) {
            System.out.println(0);
            return;
        }
        for (; v < sum; v++)  {
            int reminder = initValue -  v;
            boolean flag = false;
            for (int item : array) {
                reminder = reminder + v - item;
                if (reminder < 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.out.println(v);
                break;
            }
        }
    }
}
