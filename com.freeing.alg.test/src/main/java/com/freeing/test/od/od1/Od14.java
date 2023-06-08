package com.freeing.test.od.od1;

import java.util.*;

/**
 * https://renjie.blog.csdn.net/article/details/130956707
 *
 * @author yanggy
 */
public class Od14 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = Integer.parseInt(scanner.nextLine());
        String[] rals = new String[m];
        for (int i = 0; i < m; i++) {
            rals[i] = scanner.nextLine();
        }
        String[] array = scanner.nextLine().split(" ");
        compute(rals, array);
    }

    public static void compute(String[] ralation, String[] array) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String s : ralation) {
            String[] split = s.split(" ");
            List<String> list = map.computeIfAbsent(split[1], (key) -> new ArrayList<>());
            list.add(split[0]);
        }

        HashSet<String> set = new HashSet<>();
        ArrayList<String> res = new ArrayList<>();
        for (String s : array) {
            List<String> list = map.get(s);
            boolean flag = false;
            if (list != null) {
                for (String s1 : list) {
                    if (set.contains(s1)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                res.add(s);
            }
            set.add(s);
        }

        System.out.println(String.join(" ", res));
    }
}
