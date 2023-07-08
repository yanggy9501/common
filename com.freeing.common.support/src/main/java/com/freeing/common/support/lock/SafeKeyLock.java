package com.freeing.common.support.lock;

/**
 * 根据某个 key 加锁的默认实现
 *
 * @author yanggy
 */
public class SafeKeyLock<T> implements KeyLock<T> {


    /**
     * 加锁
     */
    @Override
    public void lock(T key) {

    }

    /**
     * 解锁
     */
    @Override
    public void unlock(T key) {

    }
}
