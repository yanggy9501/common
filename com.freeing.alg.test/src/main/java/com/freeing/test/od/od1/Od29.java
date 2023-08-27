package com.freeing.test.od.od1;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od29 {
    /*
19801211 5
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        func(split[0], Integer.parseInt(split[1]));
    }

    public static void func(String str, int n) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : str.toCharArray()) {
            map.compute(ch, (k, v) -> {
                if (v == null) {
                    return 1;
                }
                return v + 1;
            });
        }

        int num = (int)Math.ceil(str.length() / (n * 1.0));
        StringBuilder sb = new StringBuilder("1");
        for (int i = 0; i < num - 1; i++) {
            sb.append("0");
        }
        int start = Integer.parseInt(sb.toString()) - n;
        int wind = 0;
        HashMap<Character, Integer> countMap = new HashMap<>();
        for (int i = start; i < 1000; i++) {
            if (wind < n - 1) {
                count(i, countMap);
                wind++;
                continue;
            }
            count(i, countMap);
            if (map.equals(countMap)) {
                System.out.println(i - n + 1);
                break;
            }
            deCount(i - n + 1, countMap);
        }
    }

    public static void count(int num,  HashMap<Character, Integer> countMap) {
        String s = num + "";
        for (char ch : s.toCharArray()) {
            countMap.compute(ch, (k, v) -> {
                if (v == null) {
                    return 1;
                }
                return v + 1;
            });
        }
    }

    public static void deCount(int num,  HashMap<Character, Integer> countMap) {
        String s = num + "";
        for (char ch : s.toCharArray()) {
            Integer cnt = countMap.get(ch);
            if (cnt - 1 == 0) {
                countMap.remove(ch);
            } else {
                countMap.put(ch, cnt - 1);
            }
        }
    }
}
