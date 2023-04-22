package com.freeing.test.od;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od75 {
/*
123admyffc79pt
ssyy

123admyffc79ptaagghi2222smeersst88mnrt
ssyyfgh

abcmnq
rt
 */
    public static void main(String[] args) {
//        String s = "adffd012345 ZZZ 012454fdfd MMM 0aa100aaab UU";
//        String[] valids = s.split("[0-9a-f]+");正则表达式
//        System.out.println(Arrays.toString(valids));
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();
        System.out.println(find(s1, s2));
    }

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
        if (builder.length() > 0) {
            list.add(builder.toString());
        }
        list.sort((v1, v2) -> {
            if (distinctNumber(v2) == distinctNumber(v1)) {
                return v2.compareTo(v1);
            }
            return distinctNumber(v2) - distinctNumber(v1);
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
