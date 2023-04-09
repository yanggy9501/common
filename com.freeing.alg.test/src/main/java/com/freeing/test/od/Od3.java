package com.freeing.test.od;

import java.util.*;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127951051
 *
 * @author yanggy
 */
public class Od3 {

    public static String count(String s) {
        String[] arr = s.split(" ");

        ArrayList<String> list1 = new ArrayList<>();
        for (String s1 : arr) {
            char[] chars = s1.toCharArray();
            Arrays.sort(chars);
            list1.add(String.valueOf(chars));
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (String s1 : list1) {
            if (map.containsKey(s1)) {
                map.put(s1, map.get(s1) + 1);
            } else {
                map.put(s1, 0);
            }
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, (o1, o2) -> {
            if (o1.getValue() < o2.getValue()) {
                return 1;
            } else if (o1.getValue() == o2.getValue()){
                if (o1.getKey().length() > o2.getKey().length()) {
                    return 1;
                } else if (o1.getKey().length() == o2.getKey().length()) {
                    return o1.getKey().compareTo(o2.getKey());
                } else {
                    return -1;
                }
            } else {
                return 1;
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            for (int i = 0; i < value; i++) {
                stringBuilder.append(" ").append(key);
            }
        }
        return stringBuilder.deleteCharAt(0).toString();
    }
}
