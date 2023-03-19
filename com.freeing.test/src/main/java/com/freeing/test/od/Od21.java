package com.freeing.test.od;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * https://renjie.blog.csdn.net/article/details/128216884
 * 统计使用map
 *
 * @author yanggy
 */
public class Od21 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int inNum1 = Integer.parseInt(scanner.nextLine());
        String[] arr1 = scanner.nextLine().split(" ");
        Map<String, Long> map1 = Arrays.stream(arr1).collect(Collectors.groupingBy(k -> k, Collectors.counting()));

        int inNum2 = Integer.parseInt(scanner.nextLine());
        String[] arr2 = scanner.nextLine().split(" ");
        Map<String, Long> map2 = Arrays.stream(arr2).collect(Collectors.groupingBy(k -> k, Collectors.counting()));

        long max = 0;
        for (String key : map1.keySet()) {
            Long n1 = map1.get(key);
            Long n2 = map2.get(key);
            if (n1 != null && n2 != null) {
                max = Math.max(max, n1 * n2);
            }
        }
        System.out.println(max);
    }
}
