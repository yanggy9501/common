package com.freeing.common.support.lock;

import com.freeing.common.support.safe.UnsafeFactory;
import sun.misc.Unsafe;

public class CasLock {

    /**
     * 加锁标记
     * cas 不保证变量在其他地方的可见性，这里加 volatile 保证get方法获取的可见性
     */
    private volatile int state;
    private static final Unsafe UNSAFE;
    private static final long OFFSET;

    static {
        try {
            UNSAFE = UnsafeFactory.getUnsafe();
            OFFSET = UnsafeFactory.getFieldOffset(UNSAFE, CasLock.class, "state");
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public boolean lock() {
        return UNSAFE.compareAndSwapInt(this, OFFSET, 0, 1);
    }

    public boolean unlock() {
        return UNSAFE.compareAndSwapInt(this, OFFSET, 1, 0);
    }

    public int getState() {
        return state;
    }
}
