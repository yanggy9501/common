package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128468611
 *
 * @author yanggy
 */
public class Od72 {
    /*
2 2
200 200
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int person = scanner.nextInt();
        int volunteer = scanner.nextInt();
        int[] efficiency = new int[person];
        for (int i = 0; i < person; i++) {
            efficiency[i] = scanner.nextInt();
        }
        System.out.println(best(efficiency, person, volunteer));
    }

    public static int best(int[] efficiency, int person, int volunteer) {
        int[] peer = new int[person];
        // 每个人的志愿者个数
        int[] volunteerCount = new int[person];
        // 初始化每个采用人员的最终效率
        for (int i = 0; i < efficiency.length; i++) {
            peer[i] = (int) (efficiency[i] * 0.8);
        }
        // 分配志愿者：一个一个分给谁谁的效率增值更大就给谁
        int count = volunteer;
        while (count > 0) {
            count--;

            int index = 0;
            int max = 0;
            for (int i = 0; i < efficiency.length; i++) {
                // 4 中情况
                if (volunteerCount[i] == 0) {
                    if (max < (int) (efficiency[i] * 0.2)) {
                        index = i;
                        max = (int) (efficiency[i] * 0.2);

                    }
                }
                // 1m - 3m 多了给下标 0
                if (volunteerCount[i] >= 1 && volunteerCount[i] <= 3) {
                    if (max < (int) (efficiency[i] * 0.1)) {
                        index = i;
                        max = (int) (efficiency[i] * 0.1);
                    }
                }
            }
            peer[index] += max;
            volunteerCount[index] += 1;
        }
        return Arrays.stream(peer).sum();
    }
}
