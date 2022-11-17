package com.freeing.common.support.observer;

/**
 * 抽象观察者，观察者需要监听被观察者
 *
 * @author yanggy
 */
public interface IObserver {
    /**
     * 监听方法
     *
     * @param event 通知事件
     */
    void listen(NotifyEvent event);
}
