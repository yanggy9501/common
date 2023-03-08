package com.freeing.common.component.utils.queue;

import com.freeing.common.component.exception.EmptyQueueException;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LinkedList列表实现的队列
 *
 * @author yanggy
 */
public class LinkedQueue<T> implements Queue<T> {
    /**
     * 队列
     */
    private final Deque<T> queue;

    public LinkedQueue() {
        this.queue = new LinkedList<>();
    }

    @Override
    public void enqueue(T element) {
        queue.push(element);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty. Fail to dequeue element");
        }
        return queue.pop();
    }

    @Override
    public T getHead() {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty. Fail to dequeue element");
        }
        return queue.getFirst();
    }

    @Override
    public int length() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return length() == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }
}
