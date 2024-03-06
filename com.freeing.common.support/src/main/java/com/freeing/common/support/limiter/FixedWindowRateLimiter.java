package com.freeing.common.support.limiter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 固定窗口限流
 */
public class FixedWindowRateLimiter {
    /**
     * 计数器
     */
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * 时间窗口大小：毫秒数
     */
    private long windowSize;

    /**
     * 时间窗口右边界：毫秒数
     */
    private long windowBorder;

    /**
     * 允许的请求数阈值
     */
    private int threshold;

    /**
     * 同步锁
     */
    private Lock lock = new ReentrantLock();

    public FixedWindowRateLimiter(long windowSize, int threshold) {
        this.windowSize = windowSize;
        this.threshold = threshold;
        this.windowBorder = System.currentTimeMillis() + windowSize;
    }

    public boolean tryAcquire() {
        lock.lock();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            // 新一轮的时间窗口，重置计数器，更新时间窗口边界
            if (currentTimeMillis > windowBorder) {
                // TODO 当 currentTimeMillis - windowBorder 很大时会非常耗时
                do {
                    windowBorder += windowSize;
                } while (currentTimeMillis > windowBorder);
                count.set(0);
            }

            if (count.intValue() < threshold) {
                count.incrementAndGet();
                // 放行
                return true;
            } else {
                // 限流
                return false;
            }

        } finally {
            lock.unlock();
        }
    }
}
