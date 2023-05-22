package com.freeing.test.od;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947805
 *
 * 区间合并
 * 1. 数组
 * 2. 排序 + 扫描
 *
 * @author yanggy
 */
public class Od1 {
    public static void main(String[] args) {
        int[][] arr1 = {
            {2, 3, 1},
            {6, 9, 2},
            {0, 5, 1}
        };

        int[][] arr = {
            {3, 9, 2},
            {4, 7, 3}
        };
        func(arr);
        // System.out.println(computerBestCost2(arr1));
    }

    /**
     * 每次扫描，把不重叠的pop
     *
     * @param array
     */
    public static void func(int[][] array) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int[] ints : array) {
            nodes.add(new Node(ints[0], ints[1], ints[2]));
        }
        nodes.sort(Comparator.comparingInt(v -> v.start));
        // 小的放在前
        PriorityQueue<Node> queue = new PriorityQueue<>((v1, v2) -> Integer.compare(v2.end, v1.end));
        int max = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node poll = queue.peek();
            Node node = nodes.get(i);
            if (poll == null) {
                queue.add(node);
                continue;
            }
            // 和所有的重叠
            if (node.start < poll.end) {
                queue.add(node);
            } else {
                // 有不重叠的，把不重叠的pop
                int sum = queue.stream().map(item -> item.cost).mapToInt(v -> v).sum();
                max = Math.max(max, sum);
                // 剔除
                while (!queue.isEmpty()) {
                    if (node.start >= poll.end) {
                        queue.poll();
                    } else {
                        break;
                    }
                    poll = queue.peek();
                }
            }
        }
        if (!queue.isEmpty()) {
            int sum = queue.stream().map(item -> item.cost).mapToInt(v -> v).sum();
            max = Math.max(max, sum);
        }
        System.out.println(max);
    }

    public static class Node {
        public int start;
        public int end;
        public int cost;

        public Node(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }

    /**
     * 方法一：用一个数组来记录区间的合并情况（所有阶段的情况）
     *
     * @param array
     * @return
     */
    public static int computerBestCost1(int[][] array) {
        // 应该 0 - 区间最值
        int[] recoder = new int[50001];
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            int start = array[i][0];
            int end = array[i][1];
            int cost = array[i][2];
            max = doComputer(recoder, start, end, cost, max);
        }

        return max;
    }

    private static int doComputer(int[] recoder, int start, int end, int cost, int currentMax) {
        for (int i = start; i <= end; i++) {
            recoder[i] += cost;
            currentMax = Math.max(currentMax, recoder[i]);
        }
        return currentMax;
    }
}
