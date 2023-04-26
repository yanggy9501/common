package com.freeing.common.support.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 根据某个 key 加锁的默认实现
 *
 * @author yanggy
 */
public class DefaultLockKey<T> implements LockKey<T> {
    private final ConcurrentHashMap<T, ReentrantLock> LOCAL_LOCK_MAP = new ConcurrentHashMap<>();

    /**
     * 加锁
     */
    @Override
    public void lock(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Locked key can not be null");
        }
        // 获取或创建一个ReentrantLock对象
        ReentrantLock lock = LOCAL_LOCK_MAP.computeIfAbsent(key, k -> new ReentrantLock());
        // 获取锁
        lock.lock();
    }

    /**
     * 解锁
     */
    @Override
    public void unlock(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Unlocked key can not be null");
        }
        // 从Map中获取锁对象
        ReentrantLock lock = LOCAL_LOCK_MAP.get(key);
        // 如果没有加锁，就尝试解锁，获取的 lock 就为 null
        if (lock == null) {
            throw new IllegalArgumentException("Key{ " + key + "} is not locked");
        }
        // 其他线程持有该锁，当前不允许释放锁（一个线程上锁之后才能释放锁）
        if (!lock.isHeldByCurrentThread()) {
            throw new IllegalStateException("Current thread has not the lock {" + key + "} ，Release is not allowed.");
        }
        lock.unlock();
    }
}
