package com.freeing.common.test.lock;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire(1);
        System.out.println("--------------");
        semaphore.release();
    }
}
