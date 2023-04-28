package com.freeing.common.support.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 根据某个 key 加锁的默认实现
 * 【注意】：该默认实现存在内存泄漏的风险，LOCAL_LOCK_MAP 会不断累加直到应用重启或者 oom.
 *
 * @author yanggy
 */
public class DefaultLockKey<T> implements LockKey<T> {
    private final ConcurrentHashMap<T, ReentrantLock> LOCAL_LOCK_MAP = new ConcurrentHashMap<>();

    /**
     * 统计同一个 key 正在排队锁的个数
     */
    private final ConcurrentHashMap<T, AtomicInteger> LOCAL_LOCK_COUNT = new ConcurrentHashMap<>();

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
        AtomicInteger count = LOCAL_LOCK_COUNT.compute(key, (k, v) -> v == null ? new AtomicInteger(1) : v);
        count.incrementAndGet();
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
        // 这里存在一个问题就是 LOCAL_LOCK_MAP 会不断的累加 key，而且在这里不能 remove，因为并发上同一个 key 的锁，第一个上
        // 锁的线程在处理完之后，释放锁并 remove key 就会造成后面的同一个key 无法在 LOCAL_LOCK_MAP 中找到对应的锁，从而无法释放。
        lock.unlock();
        AtomicInteger count = LOCAL_LOCK_COUNT.get(key);
        count.decrementAndGet();
    }

    public Integer getLockedKeyCount(T key) {
        AtomicInteger count = LOCAL_LOCK_COUNT.get(key);
        if (count == null) {
            return null;
        }
        return count.get();
    }
}
