package com.freeing.common.component.utils.queue;

import com.freeing.common.component.exception.EmptyQueueException;
import com.freeing.common.component.exception.FullQueueException;

/**
 * 数组实现的队列
 *
 * @author yanggy
 */
public class ArrayQueue<T> implements Queue<T> {
    /**
     * 队列
     */
    private final Object[] queue;

    /**
     * 队头
     */
    private int head;

    /**
     * 队尾
     */
    private int rear;

    public ArrayQueue(int initSize) {
        this.queue = new Object[initSize + 1];
        this.head = rear = 0;
    }

    @Override
    public void enqueue(T element) {
        if (isFull()) {
            throw new FullQueueException("Queue is full");
        }
        rear = getIndex(rear + 1);
        queue[rear] = element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty. Fail to dequeue element");
        }
        head = getIndex(head + 1);
        T d = (T) queue[head];
        queue[head] = null;
        return d;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getHead() {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty. Fail to get head element");
        }
        return (T) queue[getIndex(head + 1)];
    }

    @Override
    public int length() {
        return rear - head >= 0 ? rear - head : rear - head + queue.length;
    }

    @Override
    public boolean isEmpty() {
        return head == rear;
    }

    @Override
    public boolean isFull() {
        return getIndex(rear + 1) == head;
    }

    private int getIndex(int index) {
        if (index == queue.length) {
            return 0;
        }
        return index;
    }
}
