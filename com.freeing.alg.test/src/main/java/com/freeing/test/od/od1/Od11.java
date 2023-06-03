package com.freeing.test.od.od1;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od11 {
    /*
["aa" , "bb","cc"]

["abc","bbc","c"]
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine()
            .replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "")
            .split(",");
        compute(split);
    }

    public static void compute(String[] array) {
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        int min = Arrays.stream(array).map(String::length).min(Integer::compare).orElse(0);
        int idx = 0;
        for (int i = 1; i <= min; i++) {
            for (String s : array) {
                set.add(s.charAt(s.length() - i));
            }
            if (set.size() != i) {
                break;
            }
            idx++;
        }
        if (idx == 0) {
            System.out.println("@Zero");
        }
        StringBuilder builder = new StringBuilder();
        Character[] characters = set.toArray(new Character[0]);
        for (int i = 0; i < idx; i++) {
            builder.append(characters[i]);
        }
        System.out.println(builder.reverse());
    }
}
