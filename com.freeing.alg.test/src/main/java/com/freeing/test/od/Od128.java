package com.freeing.test.od;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od128 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int target = scanner.nextInt();
        prefixSum[0] = 2;
        nums[0] = 2;
        for (int i = 1; i < 25; i++) {
            if (i < 10) {
                prefixSum[i] += prefixSum[i - 1] + 2;
                nums[i] = 2;
            } else if (i < 20) {
                prefixSum[i] += prefixSum[i - 1] + 4;
                nums[i] = 4;
            } else {
                prefixSum[i] += prefixSum[i - 1] + 8;
                nums[i] = 8;
            }
        }
        backTracking(100 - target, 0, 0, 0);
        System.out.println(count);
    }

    static int[] prefixSum = new int[25];
    static int[] nums = new int[25];

    static int count = 0;

    public static ArrayList<Integer> path = new ArrayList<>();

    public static void backTracking(int target, int sum, int startIndex, int dept) {
        if (dept > 3) {
            return;
        }
        // 最多错三个，并且要求当错 3 个时最后一个必须错
        if (sum == target && dept != 3) {
            System.out.println(path + " " + dept);
            count++;
            return;
        } else if (dept == 3){
            Integer index2 = path.get(2);
            Integer index1 = path.get(1);
            Integer index0 = path.get(0);
            int tmp =  prefixSum[24] - (prefixSum[index2] - nums[index2] - nums[index1] - nums[index0]);
            if (tmp == target) {
                System.out.println(path + " " + dept);
                count++;
                return;
            }
        }

        for (int i = startIndex; i < 25; i++) {
            if (i < 10) {
                sum += 2;
            } else if (i < 20) {
                sum += 4;
            } else {
                sum += 8;
            }
            path.add(i);
            backTracking(target, sum, i + 1, dept + 1);
            if (i < 10) {
                sum -= 2;
            } else if (i < 20) {
                sum -= 4;
            } else {
                sum -= 8;
            }
            path.remove(path.size() - 1);
        }
    }
}
