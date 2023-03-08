package com.freeing.common.support.link;

/**
 * 单链表节点
 *
 * @author yanggy
 */
public class SingleNode<T> {

    protected T data;
    protected SingleNode<T> next;

    public SingleNode(T data, SingleNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public SingleNode() {

    }
}
