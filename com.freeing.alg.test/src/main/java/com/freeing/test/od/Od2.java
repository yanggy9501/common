package com.freeing.test.od;

import java.util.Arrays;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127972503
 *
 * @author yanggy
 */
public class Od2 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 1};
        System.out.println(leastPerson(arr, 3));
    }

    public static int leastPerson(int[] weight, int limit) {
        // 最轻和最重搭配
        Arrays.sort(weight);

        int left = 0;
        int right = weight.length - 1;
        int count = 0;
        while (left <= right) {
            int w1 = weight[left];
            int w2 = weight[right];
            if (w1 + w2 <= limit) {
                left++;
            }
            count++;
            right--;
        }

        return count;
    }
}
