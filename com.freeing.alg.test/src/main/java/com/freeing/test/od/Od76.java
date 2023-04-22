package com.freeing.test.od;

import java.util.*;

/**
 * https://renjie.blog.csdn.net/article/details/128442197
 *
 * @author yanggy
 */
public class Od76 {
    /*

3
5
0 3 5 4 2
2 5 7 8 3
2 5 4 2 4
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[][] arr = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = scanner.nextInt();
            }
        }
        int[][] mutrix = mutrix(arr);
        for (int i = 0; i < m; i++) {
            System.out.println(Arrays.toString(mutrix[i]));
        }
    }

    public static int[][] mutrix(int[][] arr) {
        HashMap<Integer, List<Node>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                List<Node> list = map.getOrDefault(arr[i][j], new ArrayList<>());
                list.add(new Node(i, j));
                map.put(arr[i][j], list);
            }
        }

        int[][] newArr = new int[arr.length][arr[0].length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                List<Node> list = map.get(arr[i][j]);
                newArr[i][j] = findMixExcludeSelf(i, j, list);
            }
        }
        return newArr;
    }

    public static int findMixExcludeSelf(int x, int y, List<Node> list) {
        if (list.size() == 1) {
            return  -1;
        }
        int min = Integer.MAX_VALUE;
        for (Node node : list) {
            if (node.x == x && node.y == y) {
                continue;
            }
            int v = Math.abs(x - node.x) + Math.abs(y - node.y);
            min = Math.min(min, v);
        }
        return min;
    }

    public static class Node {
        Integer x;

        Integer y;

        public Node(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        public Node() {
        }
    }
}
