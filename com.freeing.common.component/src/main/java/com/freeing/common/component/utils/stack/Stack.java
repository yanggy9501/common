package com.freeing.common.component.utils.stack;

/**
 * 栈的抽象接口
 *
 * @author yanggy
 */
public interface Stack<T> {
    /**
     * 压栈
     *
     * @param element 数据元素
     */
    void push(T element);

    /**
     * 弹栈
     *
     * @return T 栈顶元素
     */
    T pop();

    /**
     * 获取栈顶元素
     *
     * @return T 栈顶元素
     */
    T getHead();

    /**
     * 获取栈的长度
     *
     * @return int 栈的大小
     */
    int length();

    /**
     * 判断栈是否为空
     *
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 判断栈是否已经栈满
     *
     * @return boolean
     */
    boolean isFull();
}
