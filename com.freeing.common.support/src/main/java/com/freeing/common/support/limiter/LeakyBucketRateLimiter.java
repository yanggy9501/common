package com.freeing.common.support.limiter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 漏桶算法
 */
public class LeakyBucketRateLimiter {
    /**
     * 漏桶容量
     */
    private int capicity;

    /**
     * 桶中现有水量
     */
    private AtomicInteger water = new AtomicInteger(0);

    /**
     * 漏水时间戳
     */
    private long leakTimestamp;

    /**
     * 速率(n/s)
     */
    private int leakRate;

    public LeakyBucketRateLimiter(int capicity, int leakRate) {
        this.capicity = capicity;
        this.leakRate = leakRate;
    }

    public boolean tryAcquire() {
        // 桶中没有水，则重新开始计算
        if (water.get() == 0) {
            leakTimestamp = System.currentTimeMillis();
            water.incrementAndGet();
            return water.get() <  capicity;
        }
        // 先漏水，计算剩余水量
        long currentTime = System.currentTimeMillis();
        // 要漏掉的水
        int leakedWater = (int) ((currentTime - leakTimestamp) / 1000 * leakRate);
        if (leakedWater != 0) {
            // 剩余的水
            int leftWater = water.get() - leakedWater;
            // 水漏光了，设置为0
            water.set(Math.max(0, leftWater));
            leakTimestamp = System.currentTimeMillis();
        }

        if (water.get() < capicity) {
            water.incrementAndGet();
            return true;
        } else {
            return false;
        }
    }
}
