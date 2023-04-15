package com.freeing.test.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * https://renjie.blog.csdn.net/article/details/128470169
 * 回溯算法
 *
 * @author yanggy
 */
public class Od70 {
    /*
500
100,200,300,500

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String budget = scanner.nextLine();
        String[] split = scanner.nextLine().split(",");
        List<Integer> list = Arrays.stream(split).map(Integer::parseInt).collect(Collectors.toList());
        findAllCombination(Integer.parseInt(budget), list);

    }
    public static void findAllCombination(int budget, List<Integer> list) {
        backTracking(budget, 0, list, list.stream().min((v1, v2) -> v1 - v2).get(), 0);
        System.out.println(result);
    }

    public static ArrayList<Integer> path = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> result = new ArrayList<>();

    public static void backTracking(int budget, int sum, List<Integer> list, int min, int index) {
        if (sum > budget) {
            return;
        }
        if (sum == budget) {
            result.add(new ArrayList<>(path));
        }
        if (sum < budget) {
            if (budget - sum < min) {
                result.add(new ArrayList<>(path));
            }
        }
        for (int i = index; i < list.size(); i++) {
            sum += list.get(i);
            path.add(list.get(i));
            backTracking(budget, sum, list, min, i);
            sum -= list.get(i);
            path.remove(path.size() - 1);
        }
    }
}
