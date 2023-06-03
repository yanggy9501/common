package com.freeing.test.od.od1;

import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od08 {
/*
1,0,0,0,1
0,0,0

 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(",");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        compute(array);
    }

    public static void compute(int[] array) {
        if (array == null || array.length == 0) {
            System.out.println(0);
            return;
        }
        if (array.length == 1 && array[0] == 0){
            System.out.println(1);
            return;
        }
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            if (i == 0 && value == 0 && i + 1 < array.length && array[i + 1] == 0) {
                count++;
                array[i] = 1;
                continue;
            }
            if (i == array.length - 1 && value == 0 && i - 1 >= 0 && array[i] == 0) {
                count++;
                array[i] = 1;
                continue;
            }
            if (value == 0 && array[i - 1] == 0 && array[i + 1] == 0) {
                count++;
                array[i] = 1;
                continue;
            }
        }
        System.out.println(count);
    }
}
