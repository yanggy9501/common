package com.freeing.test.od;

import java.util.HashMap;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128303606
 *
 * 方式2：保留字母 + 排序 然后比较
 *
 * @author yanggy
 */
public class Od51 {
    /*
abc
sdf134 A2c4b
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] arr = scanner.nextLine().split(" ");
        System.out.println(find(s, arr));

    }
    public static int find(String str1, String[] arr) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : str1.toCharArray()) {
            map.compute(ch, (k, v) -> v == null ? 1 : v + 1);
        }
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            boolean flag = true;
            HashMap<Character, Integer> map1 = new HashMap<>(map);
            for (char ch : s.toCharArray()) {
                if (ch >= 'a' && ch <= 'z') {
                    if (!map1.containsKey(ch)) {
                        flag = false;
                        break;
                    }
                    Integer n = map1.get(ch);
                    if (n == 1) {
                        map1.remove(ch);
                    } else {
                        map1.put(ch, n - 1);
                    }
                    continue;
                }
                if (ch >= 'A' && ch <= 'Z') {
                    if (!map1.containsKey(Character.toLowerCase(ch))) {
                        flag = false;
                        break;
                    }
                    Integer n = map1.get(Character.toLowerCase(ch));
                    if (n == 1) {
                        map1.remove(Character.toLowerCase(ch));
                    } else {
                        map1.put(Character.toLowerCase(ch), n - 1);
                    }
                    continue;
                }
            }
            if (map1.isEmpty() && flag) {
                return i + 1;
            }
        }
        return -1;
    }
}
