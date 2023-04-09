package com.freeing.test.od;

import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128267712
 * 可以按顺序统计来做(在 A 中保持相对顺序找 B)
 * 变量 A 的每个字符，判断是否在 B 中存在（B用map记）
 * 若存在并且上一个也存在，则该字符是合法的
 * 用 int[] B 的状态
 *  b a d c a b c
 *  b a   c
 *  1 1   1 不能统计了因为 a 之前的 b 此时a + 1 = 2 大于 b=1了
 *
 * @author yanggy
 */
public class Od33 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sa = scanner.nextLine();
        String sb = scanner.nextLine();
        System.out.println(count(sa, sb));
    }

    public static int count(String sa, String sb) {
        /*
badc
bac

bbadcac
bac
         */
        boolean[] used = new boolean[sa.length()];
        int cnt = 0;
        while (true) {
            for (int i = 0; i < sb.length(); i++) {
                for (int j = 0; j < sa.length(); j++) {
                    if (j == sa.length() - 1 && (sa.charAt(j) != sb.charAt(i) || used[j])) {
                        return cnt;
                    }
                    if (used[j]) {
                        continue;
                    }
                    if (sb.charAt(i) == sa.charAt(j)) {
                        used[j] = true;
                        if (i == sb.length() - 1) {
                            cnt++;
                        }
                        break;
                    }
                }
            }
        }
    }
}
