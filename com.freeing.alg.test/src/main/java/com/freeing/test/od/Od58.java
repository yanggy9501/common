package com.freeing.test.od;

import java.util.*;

/**
 * @author yanggy
 */
public class Od58 {
    /*
5
1 2 2 4 1

7
1 2 2 4 2 1 1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = Integer.parseInt(scanner.nextLine());
        String[] codes = scanner.nextLine().split(" ");
        findCode(codes);
    }

    public static void findCode(String[] codes) {
        HashMap<String, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < codes.length; i++) {
            List<Integer> list = map.compute(codes[i], (key, value) -> value == null ? new ArrayList<>() : value);
            list.add(i);
        }
        Optional<Map.Entry<String, List<Integer>>> max = map.entrySet().stream().max((e1, e2) -> {
            if (e2.getValue().size() == e1.getValue().size()) {
                return (e2.getValue().get(e2.getValue().size() - 1) - e2.getValue().get(0)) -
                    (e1.getValue().get(e1.getValue().size() - 1) - e1.getValue().get(0));
            }
            return e1.getValue().size() - e2.getValue().size();
        });
        Map.Entry<String, List<Integer>> entry = max.get();
        System.out.println(entry.getValue());
        System.out.println(entry.getValue().get(entry.getValue().size() - 1) - entry.getValue().get(0) + 1);
    }
}
