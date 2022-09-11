package com.freeing.common.support.observer;

/**
 * 抽象观察者，观察者需要监听被观察者
 *
 * @author yanggy
 */
public abstract class AbstractObserver {
    /**
     * 更新方法，当被观察者有更新时，调用通知方法从而调用观察者的update方法
     *
     * @param event 通知事件
     */
    public abstract void update(NotifyEvent event);
}
