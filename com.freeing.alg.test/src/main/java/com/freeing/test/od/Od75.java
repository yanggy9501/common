package com.freeing.test.od;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od75 {

    public static String find(String s1, String s2) {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (char ch : s1.toCharArray()) {
            if (Character.isDigit(ch)) {
                if (builder.length() > 0) {
                    list.add(builder.toString());
                    builder = new StringBuilder();
                }
                continue;
            }
            if (ch >= 'a' && ch <= 'f') {
                if (builder.length() > 0) {
                    list.add(builder.toString());
                    builder = new StringBuilder();
                }
                continue;
            }
            builder.append(ch);
        }
        list.sort((v1, v2) -> {
            if (v1.length() == v2.length()) {
                return v2.compareTo(v1);
            }
            return v2.length() - v1.length();
        });
        int n2 = distinctNumber(s2);
        for (String s : list) {
            if (distinctNumber(s) <= n2) {
                return s;
            }
        }
        return "Not Found";
    }

    public static int distinctNumber(String s) {
        HashSet<Character> set = new HashSet<>();
        for (char ch : s.toCharArray()) {
            set.add(ch);
        }
        return set.size();
    }
}
