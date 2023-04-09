package com.freeing.test.od;

import java.util.Scanner;
import java.util.Stack;

/**
 * https://renjie.blog.csdn.net/article/details/128345107
 *
 * @author yanggy
 */
public class Od60 {
    /*
5 2 C D +

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        func(split);
    }
    public static void func(String[] arr) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            String ch = arr[i];
            if (ch.equals("+")) {
                int score = 0;
                for (int j = 1; j <= 2 && stack.size() - j >= 0; j++) {
                    Integer n = stack.get(stack.size() - j);
                    score += n;
                }
                stack.push(score);
            } else if (ch.equals("D")) {
                if (!stack.isEmpty()) {
                    stack.add(stack.peek() * 2);
                }
            } else if (ch.equals("C")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(Integer.parseInt(ch));
            }
        }
        Integer sum = stack.stream().reduce(0, (v1, v2) -> v1 + v2);
        System.out.println(sum);
    }
}
