package com.freeing.test.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128589573
 *
 * @author yanggy
 */
public class Od80 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        int[] array = new int[split.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        int target = Integer.parseInt(scanner.nextLine());
        int min = Arrays.stream(array).min().getAsInt();
        backTracking(array, target, 0, 0, min);
        System.out.println(result);
    }


    public static ArrayList<ArrayList<Integer>> result = new ArrayList<>();
    public static ArrayList<Integer> path = new ArrayList<>();
    public static void backTracking(int[] array, int target, int startIndex, int sum, int min) {
        if (sum > target) {
            return;
        }
        if (target - sum < min) {
            ArrayList<Integer> list = new ArrayList<>(path);
            if (target - sum > 0) {
                list.add(target - sum);
            }
            result.add(new ArrayList<>(list));
            return;
        }
        for (int i = startIndex; i < array.length; i++) {
            path.add(array[i]);
            sum += array[i];
            backTracking(array, target, i, sum, min);
            sum -= array[i];
            path.remove(path.size() - 1);
        }
    }
}
