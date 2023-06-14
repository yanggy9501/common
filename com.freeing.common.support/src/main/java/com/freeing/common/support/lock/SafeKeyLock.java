package com.freeing.common.support.lock;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 根据某个 key 加锁的默认实现
 *
 * @author yanggy
 */
public class SafeKeyLock<T> implements KeyLock<T> {

    private static final String LOCK_PREFIX = "safe_lock_prefix_";

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
        // 加前缀避免外界也在对key进行加锁操作，这里加锁以包装获取锁和加锁与解锁都是原子性的，两者没有交叉执行
        synchronized (LOCK_PREFIX + key) {
            ReentrantLock lock = LOCAL_LOCK_MAP.computeIfAbsent(key, k -> new ReentrantLock());
            AtomicInteger count = LOCAL_LOCK_COUNT.compute(key, (k, v) -> v == null ? new AtomicInteger(1) : v);
            count.incrementAndGet();
            // 获取锁
            lock.lock();
        }
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
        if (!LOCAL_LOCK_MAP.containsKey(key)) {
            throw new IllegalArgumentException("Key{ " + key + "} is not locked");
        }
        // 其他线程持有该锁，当前不允许释放锁（一个线程上锁之后才能释放锁）
        if (!lock.isHeldByCurrentThread()) {
            throw new IllegalStateException("Current thread has not the lock {" + key + "} ，Release is not allowed.");
        }
        lock.unlock();
        if (Objects.equals(LOCAL_LOCK_COUNT.get(key).get(), 1L)) {
            // 双重检查，避免 remove 的过程中还有线程要获取该 key 的锁导致一些临界问题.
            synchronized (LOCK_PREFIX + key) {
                if (Objects.equals(LOCAL_LOCK_COUNT.get(key).get(), 1L)) {
                    LOCAL_LOCK_MAP.remove(key);
                    LOCAL_LOCK_COUNT.remove(key);
                }
            }
        } else {
            AtomicInteger count = LOCAL_LOCK_COUNT.get(key);
            count.decrementAndGet();
        }
    }
}
