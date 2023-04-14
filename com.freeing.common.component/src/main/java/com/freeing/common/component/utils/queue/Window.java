package com.freeing.common.component.utils.queue;

/**
 * 窗口
 *
 * @author yanggy
 */
public class Window<T> {
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

    public Window(int size) {
        this.queue = new Object[size + 1];
        this.head = rear = 0;
    }

    public void add(T element) {
        if (isFull()) {
            remove();
        }
        rear = getIndex(rear + 1);
        queue[rear] = element;
    }

    @SuppressWarnings("unchecked")
    private T remove() {
        if (isEmpty()) {
            return null;
        }
        head = getIndex(head + 1);
        T d = (T) queue[head];
        queue[head] = null;
        return d;
    }

    @SuppressWarnings("unchecked")
    public T getHead() {
        if (isEmpty()) {
            return null;
        }
        return (T) queue[getIndex(head + 1)];
    }

    public int length() {
        return rear - head >= 0 ? rear - head : rear - head + queue.length;
    }

    public boolean isFull() {
        return getIndex(rear + 1) == head;
    }

    public boolean isEmpty() {
        return head == rear;
    }

    private int getIndex(int index) {
        if (index == queue.length) {
            return 0;
        }
        return index;
    }
}
