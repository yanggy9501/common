package com.freeing.test.od.od1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanggy
 */
public class Od25 {
/*
5,3,6,-8,0,11
2,8,8,8,-1,15

5,8,11,3,6,8,8,-1,11,2,11,11
11,2,11,8,6,8,8,-1,8,15,3,-9,11
 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nums1 = scanner.nextLine().split(",");
        String[] nums2 = scanner.nextLine().split(",");

        HashMap<String, Integer> nums1Map = new HashMap<>();
        HashMap<String, Integer> nums2Map = new HashMap<>();
        for (String s : nums1) {
            nums1Map.compute(s, (k, v) -> {
                if (v == null) {
                    return 1;
                }
                return v + 1;
            });
        }
        for (String s : nums2) {
            nums2Map.compute(s, (k, v) -> {
                if (v == null) {
                    return 1;
                }
                return v + 1;
            });
        }
        HashMap<Integer, List<String>> map = new HashMap<>();
        for (String s : nums1) {
            if (nums2Map.containsKey(s)) {
                Integer cn1 = nums1Map.get(s);
                Integer cn2 = nums2Map.get(s);
                map.compute(Math.min(cn1, cn2), (k, v) -> {
                    if (v == null) {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(s);
                        return list;
                    }
                    v.add(s);
                    return v;
                });
            }
        }
        if (map.isEmpty()) {
            System.out.println("NULL");
        } else {
            ArrayList<Map.Entry<Integer, List<String>>> entries = new ArrayList<>(map.entrySet());
            entries.sort(Comparator.comparingInt(Map.Entry::getKey));
            for (Map.Entry<Integer, List<String>> entry : entries) {
                List<String> values = entry.getValue().stream().distinct()
                    .sorted(Comparator.comparingInt(Integer::parseInt)).collect(Collectors.toList());
                System.out.println(entry.getKey() + ":" + String.join(",", values));
            }
        }
    }


}
