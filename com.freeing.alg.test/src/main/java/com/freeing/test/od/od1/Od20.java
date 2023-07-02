package com.freeing.test.od.od1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od20 {

    public static void main(String[] args) {

    }

    public static int func(String[] array) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : array) {
            map.compute(s, (key, value) -> {
               if (value == null) {
                   return 1;
               }
               return value + 1;
            });
        }

        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(entries);
        list.sort((v1, v2) -> v2.getValue() - v1.getValue());

        int half = (int) Math.ceil(array.length / 2);
        int count = 0;
        int result = 0;
        for (Map.Entry<String, Integer> entry : list) {
            Integer value = entry.getValue();
            count += value;
            result++;
            if (count >= half) {
                break;
            }
        }
        return result;
    }
}
