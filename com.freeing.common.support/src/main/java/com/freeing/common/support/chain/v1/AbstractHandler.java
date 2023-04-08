package com.freeing.common.support.chain.v1;

/**
 * @author yanggy
 */
public abstract class AbstractHandler<T> {

    private AbstractHandler<T> next;

    public AbstractHandler<T> getNext() {
        return next;
    }

    public void setNext(AbstractHandler<T> next) {
        this.next = next;
    }

    public AbstractHandler() {

    }

    public AbstractHandler(AbstractHandler<T> next) {
        this.next = next;
    }

    /**
     * 责任链开始
     *
     * @param obj
     */
    public void chain(T obj) {
        // 执行本handler逻辑
        handler(obj);
        // 执行下一个handler
        if (next != null) {
            next.chain(obj);
        }
    }

    /**
     * 处理方法
     * 注意：不要在 handler 中调用 chain 方法，否则死循环
     *
     * @param obj
     */
    protected abstract void handler(T obj);
}
