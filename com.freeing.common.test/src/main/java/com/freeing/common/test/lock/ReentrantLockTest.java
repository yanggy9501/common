package com.freeing.common.test.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        try {
            System.out.println("111");
        } finally {
            lock.unlock();
//            lock.unlock(); // 报错：因为第一次释放时，独占已经解除了
        }
    }
}
