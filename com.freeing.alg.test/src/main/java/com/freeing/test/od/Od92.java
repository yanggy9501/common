package com.freeing.test.od;

import java.util.*;

/**
 * https://renjie.blog.csdn.net/article/details/128502753
 *
 * @author yanggy
 */
public class Od92 {
    /*
5
1 2 2 1 2 3 4
1 1 1 1 1 1 1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = Integer.parseInt(scanner.nextLine());
        Integer[] arr = Arrays.stream(scanner.nextLine().split(" "))
            .map(Integer::parseInt).toArray(Integer[]::new);
        Integer[] sizeArr = Arrays.stream(scanner.nextLine().split(" "))
            .map(Integer::parseInt).toArray(Integer[]::new);
        compute(arr, sizeArr, m);
    }

    public static void compute(Integer[] arr, Integer[] sizeArr, int m) {
        HashMap<Integer, Integer> countMap = new HashMap<>();
        HashSet<Integer> requiredCacheSet = new HashSet<>();
        HashMap<Integer, Integer> allMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            Integer s = arr[i];
            Integer n = countMap.getOrDefault(s, 0);
            countMap.put(s, ++n);
            if ((n - 1) * sizeArr[i] > m) {
                requiredCacheSet.add(s);
            }
            allMap.put(s, sizeArr[i]);
        }
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : allMap.entrySet()) {
            Integer value = entry.getValue();
            Integer key = entry.getKey();
            // set 可以不需要，通过countMap也能得出是否需要缓存
            if (requiredCacheSet.contains(key)) {
                sum += (value + m);
            } else {
                sum += (value * countMap.get(key));
            }
        }
        System.out.println(sum);
    }
}
