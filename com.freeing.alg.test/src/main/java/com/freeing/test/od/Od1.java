package com.freeing.test.od;

import java.util.Arrays;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947805
 *
 * 区间合并
 * 1. 数组
 * 2. 排序 + 扫描
 *
 * @author yanggy
 */
public class Od1 {
    public static void main(String[] args) {
        int[][] arr1 = {
            {2, 3, 1},
            {6, 9, 2},
            {0, 5, 1}
        };

        int[][] arr = {
            {3, 9, 2},
            {4, 7, 3}
        };

        System.out.println(computerBestCost2(arr1));
    }

    /**
     * 方法一：用一个数组来记录区间的合并情况（所有阶段的情况）
     *
     * @param array
     * @return
     */
    public static int computerBestCost1(int[][] array) {
        int[] recoder = new int[50001];
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            int start = array[i][0];
            int end = array[i][1];
            int cost = array[i][2];
            max = doComputer(recoder, start, end, cost, max);
        }

        return max;
    }

    private static int doComputer(int[] recoder, int start, int end, int cost, int currentMax) {
        for (int i = start; i <= end; i++) {
            recoder[i] += cost;
            currentMax = Math.max(currentMax, recoder[i]);
        }
        return currentMax;
    }

    /**
     * 通过排序 + 扫描
     * 到达下一个时，剔除已经结束的
     *
     * @param array
     * @return
     */
    public static int computerBestCost2(int[][] array) {
        Arrays.sort(array, (arr1, arr2) -> arr1[0] - arr2[0]);
        int curmax = 0;
        int resMax = 0;
        for (int i = 0; i < array.length; i++) {
            int start = array[i][0];
            int cost = array[i][2];

            // 将不重叠的剔除掉，即结束时间小于当前开始时间的
            for (int j = 0; j < i; j++) {
                int mayDelEnd = array[j][1];
                int mayDelCost = array[j][2];
                if (mayDelEnd <= start) {
                    curmax -= mayDelCost;
                }
            }
            // 遇到新的 重叠 + 1
            curmax += cost;
            resMax = Math.max(resMax, curmax);
        }
        return resMax;
    }
}
