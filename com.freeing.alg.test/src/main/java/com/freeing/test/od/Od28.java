package com.freeing.test.od;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128258957
 *
 * @author yanggy
 */
public class Od28 {

    public static void main(String[] args) {
        /*
ab
aabcd
1

abc
fdfdfabc
2

         */
        Scanner scanner = new Scanner(System.in);
        String in1 = scanner.nextLine();
        String in2 = scanner.nextLine();
        int number = Integer.parseInt(scanner.nextLine());
        if (in2.length() < in1.length() + number) {
            System.out.println(-1);
            return;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < in1.length(); i++) {
            Integer count = map.getOrDefault(in1.charAt(i), 0);
            map.put(in1.charAt(i), count + 1);
        }

        HashMap<Character, Integer> map2 = new HashMap<>();
        for (int i = 0; i < in2.length(); i++) {
            Integer count = map2.getOrDefault(in2.charAt(i), 0);
            map2.put(in2.charAt(i), count + 1);
            if (i < in1.length() + number - 1) {
                continue;
            }
            if (cheak(map, map2)) {
                System.out.println(i - in1.length() - number + 1);
                return;
            }
            Integer count1 = map2.get(in2.charAt(i - in1.length() - number + 1));
            map2.put(in2.charAt(i - in1.length() - number + 1), count1 - 1);
        }
        System.out.println(-1);
    }

    public static boolean cheak(HashMap<Character, Integer> map1, HashMap<Character, Integer> map2) {
        if (map2.size() < map1.size()) {
            return false;
        }
        for (Map.Entry<Character, Integer> entry : map1.entrySet()) {
            if (!map2.containsKey(entry.getKey())) {
                return false;
            }
        }
        return true;
    }
}
