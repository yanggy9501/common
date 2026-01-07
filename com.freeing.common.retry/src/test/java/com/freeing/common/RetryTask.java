package com.freeing.common;

import com.freeing.common.retry.config.RetryProperties;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryTask implements Runnable {
    private final AtomicInteger retryCount = new AtomicInteger();
    private RetryProperties retryProperties;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public RetryTask(RetryProperties retryProperties, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        this.retryProperties = retryProperties;
        this.scheduledThreadPoolExecutor = scheduledThreadPoolExecutor;
    }

    @Override
    public void run() {
        // TODO: 2022/4/5
        if (retryProperties.getMaxAttempts() <= 0) {
            System.out.println("重试任务");
            this.retryCount.incrementAndGet();
            scheduledThreadPoolExecutor.schedule(this, retryProperties.getDelay(), TimeUnit.MILLISECONDS);
        }
    }
}
