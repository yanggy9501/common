package com.factory;

import com.freeing.common.support.factory.Factory;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author yanggy
 */
public class MyFactory {
//    private static final Factory FACTORY = new Factory(Arrays.asList("com.factory"), MyFactory.class);
    private static final Factory FACTORY = new Factory(Arrays.asList("f.properties"), MyFactory.class.getClassLoader());

    static {
        FACTORY.init();
    }

    private MyFactory() {

    }

    public static void get() throws IOException {
        TestA testA = FACTORY.get(TestA.class);
        testA.testP();

//        FileReader fileReader = new FileReader("D:\\Project\\com.freeing.common\\com.freeing.common.support\\src\\test\\resources\\f.properties");
//        char[] chars = new char[1024];
//        fileReader.read(chars, 0, 1024);
//        System.out.println(new String(chars));

        TestB testB = FACTORY.get(TestB.class);
        testB.testP();
    }

}
