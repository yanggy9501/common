package com.freeing.test.od.od1;

import java.util.HashMap;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/130936796
 *
 * @author yanggy
 */
public class Od21 {
    /*
6,3,1,6
3

5,6,7,5,6,7
2
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(",");
        int m = Integer.parseInt(scanner.nextLine());
        System.out.println(func(split, m));
    }

    public static int func(String[] array, int m) {
        HashMap<String, Integer> map = new HashMap<>();
        int res = -1;
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (map.containsKey(s)) {
                Integer preIdx = map.get(s);
                if (i - preIdx <= m) {
                    res = preIdx;
                    break;
                }
            }
            map.put(s, i);
        }
        return res;
    }
}
