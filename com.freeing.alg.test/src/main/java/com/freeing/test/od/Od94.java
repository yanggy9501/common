package com.freeing.test.od;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128496592
 *
 * @author yanggy
 */
public class Od94 {
    /*
3
1 3
2 4
1 4

7
1 2
2 3
2 3
4 1
4 1
4 1
4 1


5 7
6 8
5 8

     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int i = Integer.parseInt(s);
        Range[] ranges = new Range[i];
        for (int j = 0; j < i; j++) {
            String[] split = scanner.nextLine().split(" ");
            int start = Integer.parseInt(split[0]);
            int end = Integer.parseInt(split[1]);
            if (start < end) {
                ranges[j] = new Range(start + 4, end + 4);
            } else {
                ranges[j] = new Range(start, end + 4);
            }
        }
        compute(ranges);

    }

    public static void compute(Range[] array) {
        Arrays.sort(array);
        int sum = 1;
        int max = 0;
        Range pre = new Range(array[0].start, array[0].end);
        Range res = new Range(array[0].start, array[0].end);
        for (int i = 1; i < array.length; i++) {
            Range range = array[i];
            if (range.start <= pre.end) {
                sum++;
                pre.end = Math.min(pre.end, range.end);
                pre.start = Math.max(pre.start, range.start);
                if (sum > max) {
                    res.start = pre.start;
                    res.end = pre.end;
                }
            } else {
                pre.start = res.start;
                pre.end = res.end;
            }
        }
        System.out.println(res.start > 4 ? res.start - 4 : res.start);
    }


    static class Range implements Comparable<Range> {
        public int start;

        public int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
        @Override
        public int compareTo(Range o) {
            return Integer.compare(start, o.start);
        }
    }
}


