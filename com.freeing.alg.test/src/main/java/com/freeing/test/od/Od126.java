package com.freeing.test.od;

import java.util.*;

/**
 * https://renjie.blog.csdn.net/article/details/129993330
 *
 * @author yanggy
 */
public class Od126 {
    /*
4
6
2 3 10
2 4 20
1 3 15
1 4 25
3 4 8
1 4 16
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] rangeArr = new int[m][3];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 3; j++) {
                rangeArr[i][j] = scanner.nextInt();
            }
        }
        func(rangeArr, n, m);
    }

    public static void func(int[][] rangeArr, int N, int M) {
        int[] distr = new int[N + 1];
        Arrays.fill(distr, Integer.MAX_VALUE);

        for (int[] itemArr : rangeArr) {
            int value0 = itemArr[0];
            int value1 = itemArr[1];
            int value2 = itemArr[2];
            for (int i = value0; i <= value1; i++) {
                distr[i] = Math.min(value2, distr[i]);
            }
        }
        System.out.println(Arrays.stream(distr).filter(item -> item != Integer.MAX_VALUE).sum());
    }

    public static void func2(List<Range<Integer, Integer>> rangeList) {
        rangeList.sort(Comparator.comparing(Range::getStart));
        ArrayList<MergeRange<Integer, Integer>> mergeRangeList = new ArrayList<>();
        for (int i = 0; i < rangeList.size(); i++) {
            Range<Integer, Integer> aRange = rangeList.get(i);
            if (mergeRangeList.isEmpty()) {
                MergeRange<Integer, Integer> aMergeRange = new MergeRange<>();
                aMergeRange.setStart(aRange.start);
                aMergeRange.setEnd(aRange.getEnd());
                aMergeRange.getChildRanges().add(aRange);
                mergeRangeList.add(aMergeRange);
                continue;
            }
            MergeRange<Integer, Integer> lastMergeRange = mergeRangeList.get(mergeRangeList.size() - 1);
            if (aRange.getStart() <= lastMergeRange.getEnd()) {
                lastMergeRange.setEnd(Math.max(aRange.getEnd(), lastMergeRange.getEnd()));
                List<Range<Integer, Integer>> childRanges = lastMergeRange.getChildRanges();
                for (int j = childRanges.size() - 1; j >= 0 ; j--) {
                    Range<Integer, Integer> childRange0 = childRanges.get(j);
                    if (aRange.getStart() <= childRange0.getStart()) {

                    }
                }
            }
        }
    }

    public static class Range<T, V> {
        private T start;

        private T end;

        private V data;

        public T getStart() {
            return start;
        }

        public void setStart(T start) {
            this.start = start;
        }

        public T getEnd() {
            return end;
        }

        public void setEnd(T end) {
            this.end = end;
        }

        public V getData() {
            return data;
        }

        public void setData(V data) {
            this.data = data;
        }
    }

    public static class MergeRange <T, V> extends Range<T, V> {

        private List<Range<T, V>> childRanges = new ArrayList<>();

        public List<Range<T, V>> getChildRanges() {
            return childRanges;
        }

        public void setChildRanges(List<Range<T, V>> childRanges) {
            this.childRanges = childRanges;
        }
    }
}
