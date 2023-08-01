package com.freeing.common.component.util.queue;

/**
 * 队列抽象接口
 *
 * @author yanggy
 */
public interface Queue<T> {
    /**
     * 入队
     *
     * @param element 入队元素
     */
    void enqueue(T element);

    /**
     * 出队
     *
     * @return 队头元素
     */
    T dequeue();

    /**
     * 获取队头元素
     *
     * @return 队头元素
     */
    T getHead();

    /**
     * 获取队列长度，即返回队列中有多少个元素长度
     *
     * @return int 队列的长度
     */
    int length();

    /**
     * 判断队列是否为空
     *
     * @return boolean true则队列为空，否则队列非空
     */
    boolean isEmpty();

    /**
     * 判断队列是否已满
     *
     * @return boolean true则队已满，否则队列非满
     */
    boolean isFull();
}
