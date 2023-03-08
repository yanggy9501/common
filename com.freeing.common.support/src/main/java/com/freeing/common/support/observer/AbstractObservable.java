package com.freeing.common.support.observer;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 被观察者
 *
 * @author yanggy
 */
public abstract class AbstractObservable {
    /**
     * 被观察对象的观察者列表
     */
    private final CopyOnWriteArrayList<IObserver> observers = new CopyOnWriteArrayList<>();

    /**
     * 添加观察者
     *
     * @param obs 观察者
     */
    public final void addObserver(IObserver obs) {
        this.observers.add(obs);
    }

    /**
     * 移除观察者
     *
     * @param obs 观察者
     */
    public final void removeObserver(IObserver obs) {
        this.observers.remove(obs);
    }

    /**
     * 同步通知所有观察者
     *
     * @param event 通知事件
     */
    public final void notifyObservers(NotifyEvent event) {
        for (IObserver observer : observers) {
            observer.listen(event);
        }
    }
}
