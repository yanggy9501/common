package com.freeing.test.od.od1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanggy
 */
public class Od07 {
    /*
1,3,3,3,2,4,4,4,5
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(",");
        int[] ints = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            ints[i] = Integer.parseInt(split[i]);
        }
        compute(ints);
    }

    public static void compute(int[] array) {
        HashMap<Integer, Node> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(array[i])) {
                Node node = map.get(array[i]);
                node.cnt++;
            } else {
                Node node = new Node();
                node.cnt = 1;
                node.firstPoint = i;
                node.value = array[i];
                map.put(array[i], node);
            }
        }
        Collection<Node> values = map.values();
        ArrayList<Node> nodes = new ArrayList<>(values);
        nodes.sort((v1, v2) -> {
            if (v1.cnt == v2.cnt) {
                return v1.firstPoint - v2.firstPoint;
            }
            return v2.cnt -  v1.cnt;
        });
        List<Integer> collect = nodes.stream().map(n -> n.value).collect(Collectors.toList());
        System.out.println(collect);
    }

    static class Node {
        int cnt;
        int firstPoint;
        int value;
    }
}
