package com.freeing.test.od;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128436530
 * 栈和队列的应用
 *
 *
 * @author yanggy
 */
public class Od37 {
    public static void main(String[] args) {
        /*
 yM eman si boB.

woh era uoy ? I ma enif.

         */
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(reverse(s));
    }

    public static String reverse(String words) {
        Deque<Character> stack = new LinkedList<>();
        LinkedList<Character> queue = new LinkedList<>();

        for (char ch : words.toCharArray()) {
            if (ch <= 'z' && ch >= 'a' || ch <= 'Z' && ch >= 'A') {
                stack.push(ch);
            } else {
                while (!stack.isEmpty()) {
                    queue.add(stack.pop());
                }
                queue.add(ch);
            }
        }
        // 最后处理
        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : queue) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }
}
