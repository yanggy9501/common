package com.freeing.test.od;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128052600
 *
 * @author yanggy
 */
public class Od9 {

    /*
5
head add 1
tail add 2
remove
head add 3
tail add 4
head add 5
remove
remove
remove
remove
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String numberStr = scanner.nextLine();
        int cnt = Integer.parseInt(numberStr);
        LinkedList<String> list = new LinkedList<>();
        int count = 0;
        int del = 1;
        for (int i = 0; i < cnt * 2 - 1; i++) {
            String in = scanner.nextLine();
            if (in.startsWith("head")) {
                list.addFirst(in.substring(in.lastIndexOf(" ") + 1));
//                System.out.println("add");
            } else if (in.startsWith("tail")) {
                list.addLast(in.substring(in.lastIndexOf(" ") + 1));
//                System.out.println("add");
            } else {
                String first = list.getFirst();
                System.out.println("del start");
                System.out.println(list);
                if (first.equals(del + "")) {

                } else {
                    Collections.sort(list);
                    count++;
                }
                list.removeFirst();
                del++;
                System.out.println(list);
                System.out.println("del end");
            }
        }
        System.out.println("结果：" + count);
    }
}
