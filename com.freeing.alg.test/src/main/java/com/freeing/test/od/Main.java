package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] positions = Arrays.stream(in.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int result = 0;

        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == 0) {
                //考虑左边界
                boolean flag1 = i == 0 || positions[i - 1] == 0;
                //考虑右边界
                boolean flag2 = i == positions.length - 1 || positions[i + 1] == 0;
                if (flag1 && flag2) {
                    result++;
                    positions[i] = 1;
                    i++;
                }
            }
        }

        System.out.println(result);
        return;
    }
}