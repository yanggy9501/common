package com.freeing.test.od;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od25 {
    /*
h he hel hell hello
b ereddred bw bww bwwl bwwlm bwwln
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");
        HashSet<String> all = new HashSet<>(Arrays.asList(input));

//        String pwd = input[0];
        String pwd = "";
        boolean modified = false;
        for (String s : input) {
            boolean flag = true;
            for (int i = 1; i < s.length() - 1; i++) {
                String subString = s.substring(0, i);
                if (!all.contains(subString)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                modified = true;
                pwd = pwd.compareTo(s) > 0 ? pwd : s;
            }
        }
        System.out.println(pwd);
//        if (modified) {
//            System.out.println(pwd);
//        } else {
//            System.out.println("");
//        }
    }
}
