package com.freeing.test.od;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://renjie.blog.csdn.net/article/details/128250019
 *
 * @author yanggy
 */
public class Od27 {
    /*
4
4
2,3,2
1,2
5
结果：[[4], [2, 3], [1, 2], [5]]


6
10
4,2,1
9
3,6,9,2
6,3,4
8
结果：
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = Integer.parseInt(scanner.nextLine());
        HashMap<String, Set<String>> map = new HashMap<>();
        for (int i = 0; i < number; i++) {
            String[] split = scanner.nextLine().split(",");
            map.put(String.valueOf(i), new HashSet<>(Arrays.asList(split)));
        }

        for (int i = 0; i < map.size(); i++) {
            Set<String> set1 = map.get(String.valueOf(i));
            if (set1.isEmpty()) {
                continue;
            }
            for (int j = 0; j < map.size(); j++) {
                Set<String> set2 = map.get(String.valueOf(j));
                if (j == i || set2.isEmpty()) {
                    continue;
                }

                HashSet<Object> retain = new HashSet<>(set1);
                retain.retainAll(set2);
                if (retain.size() > 1) {
                    set1.addAll(set2);
                    set2.clear();
                    // 合并了重来，之前的是不是可以连了
                    i = 0;
                }
            }
        }
        List<ArrayList<String>> result = map.entrySet().stream()
            .filter(entry -> !entry.getValue().isEmpty())
            .map(entry -> {
                Set<String> value = entry.getValue();
                ArrayList<String> list = new ArrayList<>(value);
                list.sort(String::compareTo);
                return list;
            }).collect(Collectors.toList());

        System.out.println(result);
    }
}
