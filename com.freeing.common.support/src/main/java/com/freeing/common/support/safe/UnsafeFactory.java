package com.freeing.common.support.safe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeFactory {

    /**
     * 获取 Unsafe 对象
     *
     * @return Unsafe
     */
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new IllegalStateException("Fail to get Unsafe");
        }
    }

    /**
     * 获取字段的内存偏移量
     *
     * @param unsafe Unsafe
     * @param clazz Class
     * @param fieldName fieldName
     * @return long
     */
    public static long getFieldOffset(Unsafe unsafe, Class<?> clazz, String fieldName) {
        try {
            return unsafe.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }
}
