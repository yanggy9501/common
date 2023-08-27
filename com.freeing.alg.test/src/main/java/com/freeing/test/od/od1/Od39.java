package com.freeing.test.od.od1;

import java.util.*;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od39 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line1 = scanner.nextLine().split(" ");
        int target = Integer.parseInt(line1[0]);
        int m = Integer.parseInt(line1[1]);
        int[] array = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 校验

        backtracking(target, array, 0, 0, 1);
        if (result.isEmpty()) {
            System.out.println(mayPath);
        } else {
            System.out.println(result);
        }
    }

    static List<Integer> path = new ArrayList<>();
    static Set<Integer> setIndex = new HashSet<>();
    static List<Integer> mayPath = null;
    static int tmpMax = 0;
    static List<List<Integer>> result = new ArrayList<>();
    public static void backtracking(int target, int[] array, int cnt, int sum, int direct) {
        if (cnt > array.length) {
            return;
        }
        if (cnt == array.length) {
            if (sum == target) {
                // 收集
                result.add(new ArrayList<>(path));
            } else {
                int tmpS = path.stream().mapToInt(v -> v).sum();
                if (tmpS < target) {
                    if (tmpS > tmpMax) {
                        tmpMax = tmpS;
                        mayPath = new ArrayList<>(path);
                    }
                }
            }
        }

        for (int i = 0; i < array.length; i++) {
            int v = array[i];
            // 注意元素可能存在重复
            if (setIndex.contains(i)) {
                continue;
            }
            sum = sum + direct * v;
            setIndex.add(i);
            path.add(v);
            backtracking(target, array, cnt + 1, sum, -direct);
            sum = sum - direct * v;
            path.remove(path.size() - 1);
            setIndex.remove(i);
        }
    }
}
