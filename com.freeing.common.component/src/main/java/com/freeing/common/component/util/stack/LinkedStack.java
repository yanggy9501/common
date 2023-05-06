package com.freeing.common.component.util.stack;

import com.freeing.common.component.util.stack.exception.EmptyStackException;

import java.util.LinkedList;

/**
 * LinkedList实现的栈
 *
 * @author yanggy
 */
public class LinkedStack<T> implements Stack<T> {
    /**
     * 栈
     */
    private final LinkedList<T> stack;

    public LinkedStack() {
        this.stack = new LinkedList<>();
    }

    @Override
    public void push(T element) {
        stack.addLast(element);
    }

    /**
     * 弹栈，弹栈时应该判断栈是否为空
     *
     * @return 栈顶元素
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.removeLast();
    }

    @Override
    public T getHead() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.getFirst();
    }

    @Override
    public int length() {
        return stack.size();
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
