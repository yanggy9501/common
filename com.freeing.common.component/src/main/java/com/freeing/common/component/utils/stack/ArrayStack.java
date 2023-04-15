package com.freeing.common.component.utils.stack;

import com.freeing.common.component.utils.stack.exception.EmptyStackException;
import com.freeing.common.component.utils.stack.exception.FullStackException;

/**
 * 数组实现的栈
 *
 * @author yanggy
 */
public class ArrayStack<T> implements Stack<T> {
    /**
     * 栈
     */
    private final Object[] stack;

    /**
     * 栈顶指针
     */
    private int top;

    public ArrayStack(int initSize) {
        this.stack = new Object[initSize];
        top = -1;
    }

    /**
     * 压栈，压栈时应该判断当前栈是否已经满
     *
     * @param element 数据元素
     */
    @Override
    public void push(T element) {
        if (isFull()) {
            throw new FullStackException("Stack is full. Fail to push a element to stack");
        }
        stack[++top] = element;
    }

    /**
     * 弹栈，弹栈时应该判断栈是否为空
     *
     * @return 栈顶元素
     */
    @Override
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) stack[top--];
    }

    /**
     * 获取栈顶元素，获取时应判断栈是否为空栈
     *
     * @return 栈顶元素
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getHead() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) stack[top];
    }

    @Override
    public int length() {
        return top + 1;
    }

    @Override
    public boolean isEmpty() {
        return top < 0;
    }

    @Override
    public boolean isFull() {
        return top == stack.length - 1;
    }
}
