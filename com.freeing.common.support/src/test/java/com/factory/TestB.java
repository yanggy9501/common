package com.factory;

import com.freeing.common.support.factory.FactoryType;

/**
 * @author yanggy
 */
@FactoryType(of = MyFactory.class)
public class TestB {
    public void testP() {
        System.out.println("hello wrold BBBBB");
    }
}
