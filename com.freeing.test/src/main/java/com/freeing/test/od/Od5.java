package com.freeing.test.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od5 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = Integer.parseInt(scanner.nextLine());
        String[] split = scanner.nextLine().split(" ");
        int[] arr = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            arr[i] = Integer.parseInt(split[i]);
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            if (canPartitionSubsets(arr, i)) {
                System.out.println(Arrays.stream(arr).sum() / i);
                return;
            }
        }
    }

    /*
2
4 3 2 3 5 2 1

 */
    public static boolean canPartitionSubsets(int[] arr, int k) {
        int sum = Arrays.stream(arr).sum();
        if (sum % k != 0) {
            return false;
        }
        int avg = sum / k;
        return backTracking(arr, new boolean[arr.length], 0, k, avg ,0, 0);
    }

    public static List<List<Integer>> result = new ArrayList<>();
    public static ArrayList<Integer> path = new ArrayList<>();
    public static boolean backTracking(int[] arr, boolean[] used, int curK, int k, int target, int curSum, int startIndex) {
        if (curSum > target) {
            return false;
        }

        if (curK == k - 1) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                if (!used[i]) {
                    list.add(arr[i]);
                }
            }
            result.add(list);
            return true;
        }

        if (curSum == target) {
            ArrayList<Integer> list = new ArrayList<>(path);
            result.add(new ArrayList<>(list));
            path.clear();
            // 进行下一组
            boolean flag = backTracking(arr, used, curK + 1, k, target, 0, 0);
            if (flag) {
                return true;
            } else {
                result.remove(list);
                return false;
            }
        }

        // 回溯：的宽度
        for (int i = 0; i < arr.length; i++) {
            if (used[i]) {
                continue;
            }
            used[i] = true;
            curSum += arr[i];
            path.add(arr[i]);
            if (backTracking(arr, used, curK, k, target, curSum, i + 1)) {
                return true;
            }
            used[i] = false;
            path.remove(Integer.valueOf(arr[i]));
            curSum -= arr[i];
        }
        return false;
    }
}
