package com.freeing.test.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

/**
 * @author yanggy
 */
public class MyConsumer<E> implements Runnable {
    protected BlockingQueue<E> queue;

    public Consumer<InterruptedException> exceptionConsumer = Throwable::printStackTrace;

    public MyConsumer(BlockingQueue<E> queue) {
        this.queue = queue;
    }

    public MyConsumer(BlockingQueue<E> queue, Consumer<InterruptedException> exceptionConsumer) {
        this.queue = queue;
        this.exceptionConsumer = exceptionConsumer;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            for (;;) {
                E e = queue.take();
                System.out.println(Thread.currentThread().toString() + e);
            }
        } catch (InterruptedException e) {
            exceptionConsumer.accept(e);
        }
    }
}
