package com.freeing.common.support.link;

import org.apache.poi.ss.formula.functions.T;

/**
 * 单链表
 *
 * @author yanggy
 */
public class SingleLink {
    private int length;

    private SingleNode<T> first;

    private SingleNode<T> last;

    public void enq(SingleNode<T> node) {
        for(;;) {
            SingleNode<T> tail = last;
            if (tail == null) {
                SingleNode<T> t = new SingleNode<>();
                first = last = t;
            } else {
                last.next = node;
                last = last.next;
                break;
            }
        }
    }
}
