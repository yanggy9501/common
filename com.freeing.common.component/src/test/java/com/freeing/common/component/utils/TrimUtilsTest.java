package com.freeing.common.component.utils;

import org.junit.Test;

public class TrimUtilsTest {

    @Test
    public void testTrim() {

        User user = new User("   name   ", 19, null);
        User user1 = new User("   n  ame   ", 19, "love   ");
        System.out.println(user);
        System.out.println(user1);
        TrimUtils.trim(user);
        TrimUtils.trim(user1);
        System.out.println(user);
        System.out.println(user1);
    }

    @Test
    public void testTrim1() {

        User user = new User("  @ 测试汉字  &* ", 19, "");
        System.out.println(user);
        TrimUtils.trim(user);
        System.out.println(user);
    }
}
