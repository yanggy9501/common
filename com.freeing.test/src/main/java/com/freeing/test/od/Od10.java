package com.freeing.test.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * https://renjie.blog.csdn.net/article/details/128052253
 *
 * @author yanggy
 */
public class Od10 {
    public static void main(String[] args) {
        /*
        h he hel hell hello o ok n ni nin ninj ninja
         */
        Scanner scanner = new Scanner(System.in);
        String[] arr = scanner.nextLine().split(" ");
        Set<String> set = Arrays.stream(arr).collect(Collectors.toSet());
        ArrayList<String> pwdList = new ArrayList<>();
        for (String s : arr) {
            boolean flag = true;
            for (int i = 1; i <= s.length(); i++) {
                if (!set.contains(s.substring(0, i))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                // 可以在这里直接判断，是否比之前的密码大
                pwdList.add(s);
            }
        }
        pwdList.sort((v1, v2) -> {
            if (v1.length() == v2.length()) {
                return v2.compareTo(v1);
            } else {
                return v2.length() - v1.length();
            }
        });
        System.out.println(pwdList.get(0));
    }
}
