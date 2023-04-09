package com.freeing.test.od;

import java.util.*;

/**
 * https://renjie.blog.csdn.net/article/details/128217281
 * 区间合并
 * 区间覆盖可能有多种情况，通过排序之后只有一种情况
 *   -----
 * -----
 * 或者
 * ----
 *   ------
 * 使用贪心 + 堆（自动排序）
 * 每当遍历到一个新的线段时，是否有重叠区间，只需要判断它起点在它之前的线段的终点
 * 是否小于自己的起点
 * 大于等于：重叠
 * 否则：不重叠，就可以删除了，其也不会与后面的重叠
 *
 * 本题求最小线段数量，当重叠数为 3 时就可以去掉其中一条了
 *
 * @author yanggy
 */
public class Od22 {
    public static void main(String[] args) {
        /*
3
1,4
2,5
3,6
         */
        Scanner scanner = new Scanner(System.in);
        ArrayList<Line> lines = new ArrayList<>();
        String n = scanner.nextLine();
        for (int i = 0; i < Integer.parseInt(n); i++) {
            String[] line = scanner.nextLine().split(",");
            lines.add(new Line(Integer.parseInt(line[0]), Integer.parseInt(line[1])));
        }
        lines.sort((v1, v2) -> {
            if (v1.start == v2.start) {
                return v2.end - v1.end;
            }
            return v1.start - v2.start;
        });

        PriorityQueue<Line> heap = new PriorityQueue<>(Comparator.comparingInt(l -> l.end));
        for (Line line : lines) {
            if (heap.isEmpty()) {
                heap.add(line);
                continue;
            }
            int start = line.start;
            heap.removeIf(lineObj -> lineObj.end < start);
            heap.add(line);
            if (heap.size() > 2) {
                // 删除多余重叠的节点
                Optional<Line> end = heap.stream().max(Comparator.comparingInt(l -> l.end));
                Optional<Line> head = heap.stream().min(Comparator.comparingInt(l -> l.end));
                heap.clear();
                heap.add(end.get());
                heap.add(head.get());
            }
        }
        System.out.println(heap.size());

        heap.stream().forEach(item -> System.out.println(item));
    }

    static class Line {
        int start;

        int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Line{" +
                "start=" + start +
                ", end=" + end +
                '}';
        }
    }
}
