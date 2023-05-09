package com.freeing.test.od;

import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128537628
 * 栈的应用
 *
 * @author yanggy
 */
public class Od88 {
    /*
{A3B1{C}3}3

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(decompress(s));
    }

    // 有 bug 当数字为多位数时
    public static String decompress(String compressStr) {
        ArrayDeque<Character> queue = new ArrayDeque<>();
        for (int j = 0; j < compressStr.toCharArray().length; j++) {
            char ch = compressStr.charAt(j);
            if (ch == '{' || ch == '}' || Character.isAlphabetic(ch)) {
                queue.push(ch);
            } else if (Character.isDigit(ch)) {
                StringBuilder nbuilder = new StringBuilder();
                int k;
                for (k = j; k < compressStr.length() && Character.isDigit(compressStr.charAt(k)); k++) {
                    nbuilder.append(compressStr.charAt(k));
                }
                j = k - 1;
                Character popCh = queue.pop();
                StringBuilder stringBuilder = new StringBuilder();
                // 寻找要重复的字符串
                if (popCh == '}') {
                    while (popCh != '{' && !queue.isEmpty()) {
                        popCh = queue.pop();
                        stringBuilder.append(popCh);
                    }
                    // 去掉 {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    stringBuilder.reverse();
                } else {
                    stringBuilder.append(popCh);
                }
                int n = Integer.parseInt(nbuilder.toString());
                for (int i = 0; i < n; i++) {
                    for (char c : stringBuilder.toString().toCharArray()) {
                        queue.push(c);
                    }
                }
            } else {
                // 其他不处理
            }
        }
        StringBuilder builder = new StringBuilder();
        while (!queue.isEmpty()) {
            builder.append(queue.pop());
        }
        return builder.reverse().toString();
    }
}
