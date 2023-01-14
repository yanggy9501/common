package com.freeing.test.blockingqueue.arraybqmodel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yanggy
 */
public class ArrayBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService consumerThreadPool = Executors.newFixedThreadPool(4);

        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1024);
        MyConsumer<String> consumer = new MyConsumer<>(blockingQueue);
        for (int i = 0; i < 4; i++) {
            System.out.println("准备消费...");
            consumerThreadPool.execute(consumer);
        }

        Thread.sleep(2000);
        MyProvider<String> provider = new MyProvider<>(blockingQueue);
        for (int i = 0; i < 40; i++) {
            System.out.println("生产消息：" + i);
            provider.accept("msg: " + i);
        }
    }
}
