package com.freeing.test.od;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128285310
 *
 * @author yanggy
 */
public class Od34 {

    /*

3
2
2,5,6,7,9,5,7
1,7,4,3,4

3
1,2,3,4,5,6
1,2,3
1,2,3,4
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = Integer.parseInt(scanner.nextLine());
        int n = Integer.parseInt(scanner.nextLine());
        ArrayList<int[]> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] split = scanner.nextLine().split(",");
            int[] ints = new int[split.length];
            int idx = 0;
            for (String s : split) {
                ints[idx++] = Integer.parseInt(s);
            }
            list.add(ints);
        }
        mergeArray(number, list);
    }

    public static void mergeArray(int number, List<int[]> list) {
        ArrayList<Integer> result = new ArrayList<>();
        HashMap<Integer, Integer> posMap = new HashMap<>();
        int countDown = list.size();
        while (countDown > 0) {
            for (int i = 0; i < list.size(); i++) {
                int[] arr = list.get(i);
                int pos = posMap.compute(i, (k, v) -> v == null ? 0 : v);
                if (pos + number - 1 < arr.length) {
                    for (int j = pos; j < pos + number; j++) {
                        result.add(arr[j]);
                    }
                    posMap.put(i, pos + number);
                } else {
                    for (int j = pos; j < arr.length; j++) {
                        result.add(arr[j]);
                    }
                    posMap.put(i, arr.length);
                    countDown--;
                }
            }
        }
        System.out.println(result);
    }
}
