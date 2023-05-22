package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static int result = 0;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        //先初始化所有题目的分数
        int[] num = new int[25];
        Arrays.fill(num, 2);
        for (int i = 10; i < num.length; i++) {
            if (i < 20) {
                num[i] = 4;
            } else {
                num[i] = 8;
            }
        }
        dfs (num,0, 0, 0, N);
        System.out.println(result);
    }

    public static void dfs(int[] num, int index, int score, int error_count, int N) {
        if (score == N) {
            result++;
            return;
        }
        if (score > N || error_count >= 3) return;

        for (int i = index; i < num.length; i++) {
            score += num[i];
            dfs(num,i + 1, score, error_count, N);
            score -= num[i];
            error_count++;
        }
    }
}