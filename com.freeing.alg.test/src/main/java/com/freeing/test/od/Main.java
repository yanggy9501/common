package com.freeing.test.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Main {
    public static List<List<Integer>> combines = new ArrayList<>();
    public static List<Integer> combine = new ArrayList<>(3);
    public static int res = -1;
    public static void main(String[] args) {
        // 处理输入
        Scanner in = new Scanner(System.in);
        String[] goods = in.nextLine().split(",");
        int r = in.nextInt();
        int[] goodsPrices = new int[goods.length];
        for (int i = 0; i < goods.length; i++) {
            goodsPrices[i] = Integer.parseInt(goods[i]);
        }
        Arrays.sort(goodsPrices);
        if (goodsPrices.length < 3 ||
            goodsPrices[0] + goodsPrices[1] + goodsPrices[2] > r) {
            System.out.println(-1);
            return;
        }

        takeGoods(goodsPrices, 0);
        combines.stream()
            .map(list -> sum(list))
            .sorted(Integer::compareTo)
            .forEach(sum -> {
                if (sum > res && sum <= r) {
                    res = sum;
                }
            });

        System.out.println(res);
    }

    public static void takeGoods(int[] goodsPrices, int start) {
        if (combine.size() == 3) {
            combines.add(new ArrayList<>(combine));
            return;
        }
        for (int i = start; i < goodsPrices.length; i++) {
            combine.add(goodsPrices[i]);
            takeGoods(goodsPrices, i + 1);
            combine.remove(combine.size() - 1);
        }
    }

    public static Integer sum(List<Integer> prices) {
        int sum = 0;
        for (Integer i : prices) {
            sum += i;
        }
        return sum;
    }
}