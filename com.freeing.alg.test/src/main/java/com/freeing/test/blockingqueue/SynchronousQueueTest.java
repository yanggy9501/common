package com.freeing.test.blockingqueue;

import java.util.concurrent.SynchronousQueue;

/**
 * @author yanggy
 */
public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                String s = synchronousQueue.take();
                System.out.println("同步收到：" + s);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                synchronousQueue.put("hello synchronous queue!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
