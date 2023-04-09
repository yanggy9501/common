package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128376506
 *
 * 方式二：通过价格 和 （价格 - 预算 的绝对值）一起排序
 *
 * @author yanggy
 */
public class Od38 {
    /*
10 5 6
1 2 3 4 5 6 7 8 9 10

10 4 6
10 9 8 7 6 5 4 3 2 1

6 3 1000
30 30 200 500 70 300
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int x = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        System.out.println(Arrays.toString(find(array, k, x)));
    }

    public static int[] find(int[] array, int k, int x) {
        // 校验 k 合法性， 其小于数组长度
        Arrays.sort(array);
        int left = 0, right = 0;
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                left = i - 1;
                right = i;
            } else if (i < array.length - 1){
                right = i + 1;
            }
            if (array[i] >= x) {
                break;
            }
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            if (left > 0 && right < array.length) {
                if (x - array[left] <= array[right] - x) {
                    res[i] = array[left--];
                } else {
                    res[i] = array[right++];
                }
            } else if (left > 0) {
                res[i] = array[left--];
            } else {
                res[i] = array[right++];
            }
        }
        Arrays.sort(res);
        return res;
    }
}
