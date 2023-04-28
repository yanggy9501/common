package com.freeing.test.od;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * https://renjie.blog.csdn.net/article/details/128585901
 *
 * @author yanggy
 */
public class Od81 {
    /*
5
5
0 4
1 2
1 3
2 3
2 4

5
3
0 3
0 4
1 3
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String s2 = scanner.nextLine();
        int n = Integer.parseInt(s2);
        String[] array = new String[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextLine();
        }
        func(array);
    }

    public static void func(String[] arr) {
        ArrayList<Node> nodeList = new ArrayList<>();
        for (String s : arr) {
            String[] split = s.split(" ");
            Node node = new Node();
            node.id = split[1];
            node.parentId = split[0];
            nodeList.add(node);
        }
        // 以 id 分组，找出其依赖项
        Map<String, List<Node>> group = nodeList.stream().collect(Collectors.groupingBy(item -> item.id));
        System.out.println(findDept(nodeList, group));
    }

    public static int findDept(List<Node> nodeList, Map<String, List<Node>> map) {
        int max = 0;
        for (Node node : nodeList) {
            max = Math.max(doFindDept(node.id, map), max);
        }
        return max;
    }

    public static int doFindDept(String id, Map<String, List<Node>> map) {
        int dept = 1;
        List<Node> nodes = map.get(id);
        if (nodes == null) {
            return dept;
        }
        for (Node nd : nodes) {
            dept = Math.max(dept, doFindDept(nd.parentId, map) + 1);
        }
        return dept;
    }
    public static class Node {
        String id;
        String parentId;
    }
}
