package com.freeing.test.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

/**
 * @author yanggy
 */
public class MyProvider<E> implements Consumer<E> {
    protected BlockingQueue<E> queue;

    public Consumer<InterruptedException> exceptionConsumer = Throwable::printStackTrace;

    public MyProvider(BlockingQueue<E> queue) {
        this.queue = queue;
    }

    public MyProvider(BlockingQueue<E> queue, Consumer<InterruptedException> exceptionConsumer) {
        this.queue = queue;
        this.exceptionConsumer = exceptionConsumer;
    }

    @Override
    public void accept(E e) {
        try {
            queue.put(e);
        } catch (InterruptedException ex) {
            exceptionConsumer.accept(ex);
        }
    }
}
