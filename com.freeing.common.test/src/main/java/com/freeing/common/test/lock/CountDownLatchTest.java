package com.freeing.common.test.lock;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        countDownLatch.await(); //

        System.out.println();
    }
}
