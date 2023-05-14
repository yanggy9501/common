package com.freeing.test.od;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * https://renjie.blog.csdn.net/article/details/128621868
 *
 * @author yanggy
 */
public class Od99 {
    /*
20 1

110 1100011

0 0 0 0 0 0 01

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        List<String> collect = Arrays.stream(split).collect(Collectors.toList());
        compute(collect);
    }


    public static void compute(List<String> array) {
        array.sort((v1, v2) -> (v1 + v2).compareTo(v2 + v1));
        StringBuilder builder0 = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            String s = array.get(i);
            if (s.charAt(0) == '0') {
                // 把前面的 0 开头全部收回
                builder0.append(s);
            } else {
                builder1.append(s);
                // 第一个非 0 开头的拼上 0 开头的
                if (builder0 != null) {
                    builder1.append(builder0);
                    builder0 = null;
                }
            }
        }
        if (builder0 != null) {
            builder1.append(builder0);
        }
        String join = builder1.toString();
        int index = 0;
        for (int i = 0; i < join.length(); i++) {
            char ch = join.charAt(i);
            index = i;
            if (ch != '0') {
                break;
            }
        }
        if (index == 0) {
            System.out.println(join);
        } else {
            System.out.println(join.substring(index));
        }
    }
}
