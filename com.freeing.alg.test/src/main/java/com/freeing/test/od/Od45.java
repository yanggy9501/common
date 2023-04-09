package com.freeing.test.od;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/**
 * https://renjie.blog.csdn.net/article/details/128420175
 * 
 * @author yanggy
 */
public class Od45 {

    public static void main(String[] args) {
        Stack<Integer> integers = new Stack<>();
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        int[] array = new int[i];
        for (int j = 0; j < i; j++) {
            array[j] = scanner.nextInt();
        }
        System.out.println(Arrays.toString(distributeFond(array)));
    }
    
    public static int[] distributeFond(int[] array) {
        LinkedList<Integer> stackPosMap = new LinkedList<>();
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            if (!stackPosMap.isEmpty() && array[i] > array[stackPosMap.getLast()]) {
                Integer pos = stackPosMap.removeLast();
                result[pos] = (i - pos) * (array[i] - array[pos]);
            }
            stackPosMap.addLast(i);
        }
        while (!stackPosMap.isEmpty()) {
            Integer pos = stackPosMap.removeLast();
            result[pos] = array[pos];
        }
        return result;
    }
}
