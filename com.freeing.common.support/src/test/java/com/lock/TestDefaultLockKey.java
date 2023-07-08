package com.lock;

import com.freeing.common.support.lock.DefaultKeyLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yanggy
 */
public class TestDefaultLockKey {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(16);
        DefaultKeyLock<String> lock = new DefaultKeyLock<>();

        for (int i = 0; i < 50; i++) {
            int finalI = i;
            pool.execute(() -> {
                try {
                    lock.lock(String.valueOf(finalI % 5));
                    Thread.sleep((int)(Math.random() * 1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }  finally {
                    lock.unlock(String.valueOf(finalI % 5));
                }
            });
        }
        pool.shutdown();
    }
}
