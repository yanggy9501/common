package com.freeing.test.od;

import java.util.HashMap;

/**
 * https://renjie.blog.csdn.net/article/details/127995151
 *
 * @author yanggy
 */
public class Od6 {
    public static void main(String[] args) {
        String[] arr = {"00000 3 -1", "00010 5 12309", "11451 6 00000", "12309 7 11451"};
        System.out.println(findMiddleNode(arr, "00010"));
    }

    public static String findMiddleNode(String[] array, String head) {
        HashMap<String, String> valueMap = new HashMap<>();
        HashMap<String, String> posMap = new HashMap<>();
        for (String s : array) {
            String[] arr = s.split(" ");
            String index = arr[0];
            String value = arr[1];
            String next = arr[2];
            posMap.put(index, next);
            valueMap.put(index, value);
        }
        int pos = array.length / 2 + 1;
        doLink(posMap, head, 1, pos);
        return valueMap.get(res);
    }

    public static String res = "";
    public static void doLink(HashMap<String, String> posMap, String next, int cnt, int pos) {
        if (cnt == pos) {
            res = next;
        }
        String nextPos = posMap.get(next);
        if (nextPos == null) {
            return;
        }
        doLink(posMap, nextPos, ++cnt, pos);
    }
}
