package com.freeing.test.od.od1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/130921547
 * @author yanggy
 */
public class Od17 {
    /*
3 3
1 0 0
0 1 0
0 0 1

5 3
-1 0 1
0 0 0
-1 0 0
0 -1 0
0 0 0
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[][] array = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = scanner.nextInt();
            }
        }
        func(array);
    }

    public static void func(int[][] array) {
        int cnt0_row = array[0].length / 2;
        int count_row = 0;
        int cnt0_col = array.length / 2;
        int count_col = 0;
        // 行
        for (int[] rows : array) {
            long count = Arrays.stream(rows).filter(item -> item == 0).count();
            if (count >= cnt0_row) {
                count_row++;
            }
        }
        for (int i = 0; i < array[0].length; i++) {
            int count = 0;
            for (int j = 0; j < array.length; j++) {
                if (array[j][i] == 0) {
                    count++;
                }
            }
            if (count >= cnt0_col) {
                count_col++;
            }
        }
        System.out.println(count_row);
        System.out.println(count_col);
    }
}
