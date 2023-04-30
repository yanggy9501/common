package com.freeing.common.component.utils;

import org.junit.Test;

public class ObjectUtilsTest {

    @Test
    public void testTrim() {
        // Setup
        // Run the test
        ObjectUtils.trim("object");

        // Verify the results
    }

    @Test
    public void testEncoder() {
        User user = new User("name", 19, "kato");
        System.out.println(user);
        ObjectUtils.trim(user);
        System.out.println(user);
        ObjectUtils.encoder(user);
        System.out.println(user);
    }
}
