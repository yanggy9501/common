package com.freeing.test.od;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * https://renjie.blog.csdn.net/article/details/128227044
 *
 * @author yanggy
 */
public class Od24 {
    /*
2 5 3 6 5 6
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(" ");
        List<Integer> inputList = Arrays.stream(split).map(Integer::parseInt).collect(Collectors.toList());

        int leftRes = 1;
        int rightRes = inputList.stream().skip(1).reduce(1, (pre, cur) -> pre * cur);
        if (leftRes == rightRes) {
            System.out.println(0);
            return;
        }
        for (int i = 1; i < inputList.size(); i++) {
            leftRes = leftRes * inputList.get(i - 1);
            rightRes = rightRes / inputList.get(i);
            if (leftRes == rightRes) {
                System.out.println(i);
                return;
            }
        }
        System.out.println(-1);
    }
}
