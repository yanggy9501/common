package com.freeing.common.support.link;

/**
 * 双链表节点
 *
 * @author yanggy
 */
public class DoubleNode<T> {
    private T data;

    private DoubleNode<T> prev;

    private DoubleNode<T> next;

    public DoubleNode(T data, DoubleNode<T> prev, DoubleNode<T> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public DoubleNode() {

    }
}
