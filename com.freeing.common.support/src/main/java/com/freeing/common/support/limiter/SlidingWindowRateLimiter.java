package com.freeing.common.support.limiter;

/**
 * 滑动窗口限流
 */
public class SlidingWindowRateLimiter {
    /**
     * 时间窗口大小，单位毫秒
     */
    private long windowSize;

    /**
     * 分片窗口数
     */
    private int shardNum;

    /**
     * 允许通过请求数
     */
    private int maxRequestCount;

    /**
     * 各个窗口内请求计数
     */
    private int[] shardRequestCount;

    /**
     * 请求总数
     */
    private int totalCount;

    /**
     * 当前窗口下标
     */
    private int shardId;

    /**
     * 每个小窗口大小，毫秒
     */
    private long tinyWindowSize;

    /**
     * 窗口右边界
     */
    private long windowBorder;

    public SlidingWindowRateLimiter(long windowSize, int shardNum, int maxRequestCount) {
        this.windowSize = windowSize;
        this.shardNum = shardNum;
        this.maxRequestCount = maxRequestCount;
        shardRequestCount = new int[shardNum];
        tinyWindowSize = windowSize / shardNum;
        windowBorder = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > windowBorder) {
            // TODO 当 currentTimeMillis - windowBorder 很大时会非常耗时
            do {
                shardId = (++shardId) % shardNum;
                totalCount -= shardRequestCount[shardId];
                shardRequestCount[shardId] = 0;
                windowBorder += tinyWindowSize;
            } while (windowBorder < currentTime);
        }
        if (totalCount < maxRequestCount) {
            shardRequestCount[shardId]++;
            totalCount++;
            return true;
        } else {
            return false;
        }
    }
}
