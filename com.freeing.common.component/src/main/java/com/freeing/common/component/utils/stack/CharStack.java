package com.freeing.common.component.utils.stack;

import com.freeing.common.component.utils.stack.exception.EmptyStackException;
import com.freeing.common.component.utils.stack.exception.FullStackException;

/**
 * @author yanggy
 */
public class CharStack {
    /**
     * 栈
     */
    private final char[] stack;

    /**
     * 栈顶指针
     */
    private int top;

    public CharStack(int initSize) {
        this.stack = new char[initSize];
        top = -1;
    }

    /**
     * 压栈，压栈时应该判断当前栈是否已经满
     *
     * @param element char 类型元素
     */
    public void push(char element) {
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
    public char pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack[top--];
    }

    /**
     * 获取栈顶元素
     *
     * @return 栈顶元素
     */
    public char getHead() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack[top];
    }

    public int length() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top < 0;
    }

    public boolean isFull() {
        return top == stack.length - 1;
    }
}
