package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128259596
 *
 * @author yanggy
 */
public class Od29 {
    public static void main(String[] args) {
        /*
9
5 2 1 5 2 1 5 2 1
         */
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int[] arr = new int[n];
        String[] split = scanner.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(split[i]);
        }
        for (int i = n - 1; i > 0; i--) {
            if (partitionK(arr, i)) {
                System.out.println(i);
                break;
            }
        }
    }

    public static boolean partitionK(int[] scoreArr, int k) {
        int sum = Arrays.stream(scoreArr).sum();
        if (sum % k != 0) {
            return false;
        }
        int avg = sum / k;
        return doParition(scoreArr, k, 0, 0, avg, 0, new boolean[scoreArr.length]);
    }

    public static boolean doParition(int[] scoreArr, int k, int currK, int score, int target, int startIndex, boolean[] used) {
        if (currK == k -  1) {
            return true;
        }
        // 找到了一组继续找下一组
        if (score == target) {
            // 下一轮的结果
            if (doParition(scoreArr, k, currK + 1, 0, target, 0, used)) {
                return true;
            } else {
                return false;
            }
        } else {
            // 继续本轮找
            for (int i = startIndex; i < scoreArr.length; i++) {
                if (used[i]) {
                    continue;
                }
                used[i] = true;
                score += scoreArr[i];
                // 本轮的结果，返回给上一轮看
                if (doParition(scoreArr, k, currK, score, target, i + 1, used)) {
                    return true;
                }
                score -= scoreArr[i];
                used[i] = false;
            }
        }
        // 找不到 false
        return false;
    }
}
