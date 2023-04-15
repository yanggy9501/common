package com.freeing.test.blockingqueue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author yanggy
 */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(11, Integer::compare);

        queue.put(1);
        queue.put(2);
        queue.put(10);
        queue.put(8);
        queue.put(6);
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }
}
