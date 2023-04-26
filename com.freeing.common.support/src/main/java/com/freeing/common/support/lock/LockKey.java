package com.freeing.common.support.lock;

/**
 * 根据某个 key 加锁的抽象
 *
 * @author yanggy
 */
public interface LockKey<T> {
    /**
     * 加锁
     */
    void lock(T key);

    /**
     * 解锁
     */
    void unlock(T key);
}
