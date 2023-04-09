package com.freeing.test.od;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128306423
 *
 * @author yanggy
 */
public class Od52 {
/*
ab ef
aef

ab bcd ef
cbd fe
 */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] arr1 = scanner.nextLine().split(" ");
        String[] arr2 = scanner.nextLine().split(" ");
        System.out.println(match(arr1, arr2));
    }

    public static boolean match(String[] arr, String[] texts) {
        HashSet<String> set = new HashSet<>();
        for (String s : arr) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            set.add(String.valueOf(chars));
        }
        for (String s : texts) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            if (!set.contains(String.valueOf(chars))) {
                return false;
            }
        }
        return true;
    }
}
