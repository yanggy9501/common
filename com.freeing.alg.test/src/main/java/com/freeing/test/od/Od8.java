package com.freeing.test.od;

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
        String cars = scanner.nextLine();
        String[] carArr = cars.split(" ");
        String windor = scanner.nextLine();
        int size = Integer.parseInt(windor);
        int[] colorCount = new int[3];
        for (int i = 0; i < size && i < carArr.length; i++) {
            colorCount[Integer.parseInt(carArr[i])] += 1;
        }

        // 初始化
        int max = max(colorCount);
        for (int i = size; i < carArr.length; i++) {
            colorCount[Integer.parseInt(carArr[i])] += 1;
            colorCount[Integer.parseInt(carArr[i - size])] -= 1;
            max = max(colorCount);
        }
        System.out.println(max);
    }

    public static int max(int[] arr) {
        int max = arr[0];
        for (int i : arr) {
            max = Math.max(max, i);
        }
        return max;
    }
}
