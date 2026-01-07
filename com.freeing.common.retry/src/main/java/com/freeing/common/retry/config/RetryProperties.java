package com.freeing.common.retry.config;

import java.io.Serial;
import java.io.Serializable;

public class RetryProperties implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int maxAttempts = 3;
    private long delay = 1000;


    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "RetryProperties{" +
            "maxAttempts=" + maxAttempts +
            ", delay=" + delay +
            '}';
    }
}
