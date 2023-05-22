package com.freeing.test.od;

import java.util.HashMap;
import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 * 求滑动窗口中的众数
 * 1. 遍历统计
 * 2. map
 * 3. 数组（如果数量确定）
 * 推荐2 和 3
 * 
 * @author yanggy
 */
public class Od8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // String cars = scanner.nextLine();
        // String[] carArr = cars.split(" ");
        // String windor = scanner.nextLine();
        // int size = Integer.parseInt(windor);
        // int[] colorCount = new int[3];
        // for (int i = 0; i < size && i < carArr.length; i++) {
        //     colorCount[Integer.parseInt(carArr[i])] += 1;
        // }
        //
        // // 初始化
        // int max = max(colorCount);
        // for (int i = size; i < carArr.length; i++) {
        //     colorCount[Integer.parseInt(carArr[i])] += 1;
        //     colorCount[Integer.parseInt(carArr[i - size])] -= 1;
        //     max = max(colorCount);
        // }
        // System.out.println(max);
        String[] split = scanner.nextLine().split(" ");
        func(split, 3);
    }

    public static int max(int[] arr) {
        int max = arr[0];
        for (int i : arr) {
            max = Math.max(max, i);
        }
        return max;
    }

    public static void func(String[] array, int window) {
        // 统计滑动窗口内的数据
        HashMap<String, Integer> windowMap = new HashMap<>(window);
        int max = 0;
        int windowLeft = 0;
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (i >= window) {
                Integer leftCnt = windowMap.get(array[windowLeft]);
                windowMap.put(array[windowLeft], leftCnt - 1);
                windowLeft++;
            }
            Integer cnt = windowMap.getOrDefault(s, 0);
            max = Math.max(cnt + 1, max);
            windowMap.put(s, cnt + 1);
        }
        System.out.println(max);
    }
}
