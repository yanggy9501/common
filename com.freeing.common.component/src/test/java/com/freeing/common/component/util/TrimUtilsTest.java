package com.freeing.common.component.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

public class TrimUtilsTest {

    @Test
    public void testTrim() {

        User user = new User("   name   ", 19, null);
        User user1 = new User("   n  ame   ", 19, "love   ");
        System.out.println(user);
        System.out.println(user1);
        ObjectUtils.trim(user);
        ObjectUtils.trim(user1);
        System.out.println(user);
        System.out.println(user1);
    }

    @Test
    public void testTrim1() {
        User user = new User("  @ 测试汉字  &* ", 19, "");
        System.out.println(user);
        ObjectUtils.trim(user);
        System.out.println(user);
    }

    @Test
    public void testTrim2() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", "    ");
        map.put("3", " fdf   ");
        map.put("3", "dfdf");
        System.out.println(map);
        ObjectUtils.trim(map);
        System.out.println(map);
    }

    @Test
    public void testTrim3() {
        String[] strings = {"11", "  ", "dfdfd"};
        System.out.println(Arrays.toString(strings));
        ObjectUtils.trim(strings);
        System.out.println(Arrays.toString(strings));
    }
}
