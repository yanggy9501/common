package com.freeing.test.od;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128351424
 *
 * @author yanggy
 */
public class Od62 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = scanner.nextInt();
        }
        System.out.println(maxDist(ints));
    }

    public static int maxDist(int[] arr) {
        HashMap<Integer, int[]> map = new HashMap<>();
        for (int n = 0; n < arr.length; n++) {
            int key = arr[n];
            if (map.containsKey(Integer.valueOf(key))) {
                int[] item = map.get(key);
                item[1] = n;
            } else {
                int[] item = new int[2];
                item[0] = n;
                map.put(Integer.valueOf(key), item);
            }
        }

        Optional<Map.Entry<Integer, int[]>> max = map.entrySet().stream().max((e1, e2) -> {
            int[] value1 = e1.getValue();
            int[] value2 = e2.getValue();
            return (value1[1] - value1[0]) - (value2[1] - value2[0]);
        });
        Integer s = max.map(Map.Entry::getValue).map(e -> e[1] - e[0]).get();
        return s < 0 ? -1 : s;
    }
}
